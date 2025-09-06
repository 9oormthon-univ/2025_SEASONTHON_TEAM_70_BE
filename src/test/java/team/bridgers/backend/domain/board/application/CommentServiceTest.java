package team.bridgers.backend.domain.board.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import team.bridgers.backend.domain.board.domain.Board;
import team.bridgers.backend.domain.board.domain.BoardType;
import team.bridgers.backend.domain.board.domain.Comment;
import team.bridgers.backend.domain.board.infrastructure.BoardJpaRepository;
import team.bridgers.backend.domain.board.infrastructure.CommentJpaRepository;
import team.bridgers.backend.domain.board.presentation.exeption.BoardNotFoundExeption;
import team.bridgers.backend.domain.board.presentation.exeption.CommentNotFoundException;
import team.bridgers.backend.domain.board.presentation.exeption.CommentUnauthorizedAccessExeption;
import team.bridgers.backend.domain.board.presentation.exeption.ContentLengthExceededException;
import team.bridgers.backend.domain.board.presentation.response.CommentDetailResponse;
import team.bridgers.backend.domain.board.presentation.response.CommentResponse;
import team.bridgers.backend.domain.user.domain.Gender;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.UserType;
import team.bridgers.backend.domain.user.infrastructure.UserJpaRepository;
import team.bridgers.backend.domain.user.presentation.exception.UserNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentJpaRepository commentRepository;

    @Autowired
    private BoardJpaRepository boardRepository;

    @Autowired
    private UserJpaRepository userRepository;

    private User testUser;
    private User anotherUser;
    private Board testBoard;

    @BeforeEach
    void setUp() {
        // 데이터 초기화
        commentRepository.deleteAllInBatch();
        boardRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        // 테스트 사용자 생성
        testUser = User.builder()
                .email("test@test.com")
                .nickname("testUser")
                .loginId("testId")
                .password("password")
                .gender(Gender.MALE)
                .birthday("19990101")
                .type(UserType.JOBSEEKER)
                .build();
        userRepository.save(testUser);

        anotherUser = User.builder()
                .email("another@test.com")
                .nickname("anotherUser")
                .loginId("anotherId")
                .password("password")
                .gender(Gender.FEMALE)
                .birthday("19980101")
                .type(UserType.WORKER)
                .build();
        userRepository.save(anotherUser);

        // 테스트 게시글 생성
        testBoard = Board.builder()
                .boardType(BoardType.FREE)
                .boardTitle("Test Board")
                .boardContent("Test Content")
                .user(testUser)
                .build();
        boardRepository.save(testBoard);
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAllInBatch();
        boardRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    void createComment_성공() {
        // given
        String commentContent = "테스트 댓글입니다.";

        // when
        CommentResponse response = commentService.createComment(
                testUser.getId(), testBoard.getBoardId(), commentContent);

        // then
        assertThat(response.commentId()).isNotNull();

        Comment savedComment = commentRepository.findById(response.commentId()).orElse(null);
        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getCommentContent()).isEqualTo(commentContent);
        assertThat(savedComment.getUser().getId()).isEqualTo(testUser.getId());
        assertThat(savedComment.getBoard().getBoardId()).isEqualTo(testBoard.getBoardId());
    }

    @Test
    void createComment_존재하지않는사용자_실패() {
        // given
        Long invalidUserId = 999L;
        String commentContent = "테스트 댓글";

        // when & then
        assertThrows(UserNotFoundException.class, () ->
            commentService.createComment(invalidUserId, testBoard.getBoardId(), commentContent));
    }

    @Test
    void createComment_존재하지않는게시글_실패() {
        // given
        Long invalidBoardId = 999L;
        String commentContent = "테스트 댓글";

        // when & then
        assertThrows(BoardNotFoundExeption.class, () ->
            commentService.createComment(testUser.getId(), invalidBoardId, commentContent));
    }

    @Test
    void createComment_null내용_실패() {
        // when & then
        assertThrows(IllegalArgumentException.class, () ->
            commentService.createComment(testUser.getId(), testBoard.getBoardId(), null));
    }

    @Test
    void createComment_길이초과_실패() {
        // given
        String longComment = "a".repeat(501); // 500자 초과

        // when & then
        assertThrows(ContentLengthExceededException.class, () ->
            commentService.createComment(testUser.getId(), testBoard.getBoardId(), longComment));
    }

    @Test
    void getCommentsByBoard_성공() {
        // given
        Comment comment1 = Comment.builder()
                .commentContent("첫 번째 댓글")
                .user(testUser)
                .board(testBoard)
                .build();
        Comment comment2 = Comment.builder()
                .commentContent("두 번째 댓글")
                .user(anotherUser)
                .board(testBoard)
                .build();
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        // when
        List<CommentDetailResponse> comments = commentService.getCommentsByBoard(testBoard.getBoardId());

        // then
        assertThat(comments).hasSize(2);
        assertThat(comments.get(0).commentContent()).isEqualTo("첫 번째 댓글");
        assertThat(comments.get(0).userNickname()).isEqualTo(testUser.getNickname());
        assertThat(comments.get(1).commentContent()).isEqualTo("두 번째 댓글");
        assertThat(comments.get(1).userNickname()).isEqualTo(anotherUser.getNickname());
    }

    @Test
    void getCommentsByBoard_댓글없음_빈리스트반환() {
        // when
        List<CommentDetailResponse> comments = commentService.getCommentsByBoard(testBoard.getBoardId());

        // then
        assertThat(comments).isEmpty();
    }

    @Test
    void updateComment_성공() {
        // given
        Comment comment = Comment.builder()
                .commentContent("원본 댓글")
                .user(testUser)
                .board(testBoard)
                .build();
        Comment savedComment = commentRepository.save(comment);
        String updatedContent = "수정된 댓글";

        // when
        CommentResponse response = commentService.updateComment(
                savedComment.getCommentId(), testUser.getId(), updatedContent);

        // then
        assertThat(response.commentId()).isEqualTo(savedComment.getCommentId());

        Comment updatedComment = commentRepository.findById(savedComment.getCommentId()).orElse(null);
        assertThat(updatedComment).isNotNull();
        assertThat(updatedComment.getCommentContent()).isEqualTo(updatedContent);
    }

    @Test
    void updateComment_권한없음_실패() {
        // given
        Comment comment = Comment.builder()
                .commentContent("원본 댓글")
                .user(testUser)
                .board(testBoard)
                .build();
        Comment savedComment = commentRepository.save(comment);

        // when & then
        assertThrows(CommentUnauthorizedAccessExeption.class, () ->
            commentService.updateComment(savedComment.getCommentId(), anotherUser.getId(), "수정 시도"));
    }

    @Test
    void updateComment_존재하지않는댓글_실패() {
        // given
        Long invalidCommentId = 999L;

        // when & then
        assertThrows(CommentNotFoundException.class, () ->
            commentService.updateComment(invalidCommentId, testUser.getId(), "수정 내용"));
    }

    @Test
    void updateComment_null내용_실패() {
        // given
        Comment comment = Comment.builder()
                .commentContent("원본 댓글")
                .user(testUser)
                .board(testBoard)
                .build();
        Comment savedComment = commentRepository.save(comment);

        // when & then
        assertThrows(IllegalArgumentException.class, () ->
            commentService.updateComment(savedComment.getCommentId(), testUser.getId(), null));
    }

    @Test
    void updateComment_길이초과_실패() {
        // given
        Comment comment = Comment.builder()
                .commentContent("원본 댓글")
                .user(testUser)
                .board(testBoard)
                .build();
        Comment savedComment = commentRepository.save(comment);
        String longComment = "a".repeat(501);

        // when & then
        assertThrows(ContentLengthExceededException.class, () ->
            commentService.updateComment(savedComment.getCommentId(), testUser.getId(), longComment));
    }

    @Test
    void deleteComment_성공() {
        // given
        Comment comment = Comment.builder()
                .commentContent("삭제할 댓글")
                .user(testUser)
                .board(testBoard)
                .build();
        Comment savedComment = commentRepository.save(comment);

        // when
        CommentResponse response = commentService.deleteComment(savedComment.getCommentId(), testUser.getId());

        // then
        assertThat(response.commentId()).isEqualTo(savedComment.getCommentId());
        assertThat(commentRepository.findById(savedComment.getCommentId())).isEmpty();
    }

    @Test
    void deleteComment_권한없음_실패() {
        // given
        Comment comment = Comment.builder()
                .commentContent("삭제할 댓글")
                .user(testUser)
                .board(testBoard)
                .build();
        Comment savedComment = commentRepository.save(comment);

        // when & then
        assertThrows(CommentUnauthorizedAccessExeption.class, () ->
            commentService.deleteComment(savedComment.getCommentId(), anotherUser.getId()));
    }

    @Test
    void deleteComment_존재하지않는댓글_실패() {
        // given
        Long invalidCommentId = 999L;

        // when & then
        assertThrows(CommentNotFoundException.class, () ->
            commentService.deleteComment(invalidCommentId, testUser.getId()));
    }

    @Test
    void 댓글_생성_수정_삭제_통합테스트() {
        // 댓글 생성
        CommentResponse createResponse = commentService.createComment(
                testUser.getId(), testBoard.getBoardId(), "초기 댓글");

        // 댓글 조회 및 검증
        List<CommentDetailResponse> comments = commentService.getCommentsByBoard(testBoard.getBoardId());
        assertThat(comments).hasSize(1);
        assertThat(comments.get(0).commentContent()).isEqualTo("초기 댓글");

        // 댓글 수정
        commentService.updateComment(createResponse.commentId(), testUser.getId(), "수정된 댓글");

        // 수정 결과 검증
        comments = commentService.getCommentsByBoard(testBoard.getBoardId());
        assertThat(comments.get(0).commentContent()).isEqualTo("수정된 댓글");

        // 댓글 삭제
        commentService.deleteComment(createResponse.commentId(), testUser.getId());

        // 삭제 결과 검증
        comments = commentService.getCommentsByBoard(testBoard.getBoardId());
        assertThat(comments).isEmpty();
    }

    @Test
    void 여러사용자_댓글작성_테스트() {
        // given & when
        CommentResponse response1 = commentService.createComment(
                testUser.getId(), testBoard.getBoardId(), "첫 번째 사용자 댓글");
        CommentResponse response2 = commentService.createComment(
                anotherUser.getId(), testBoard.getBoardId(), "두 번째 사용자 댓글");

        // then
        List<CommentDetailResponse> comments = commentService.getCommentsByBoard(testBoard.getBoardId());
        assertThat(comments).hasSize(2);

        // 각 댓글의 작성자 확인
        boolean hasTestUserComment = comments.stream()
                .anyMatch(comment -> comment.userNickname().equals(testUser.getNickname()));
        boolean hasAnotherUserComment = comments.stream()
                .anyMatch(comment -> comment.userNickname().equals(anotherUser.getNickname()));

        assertThat(hasTestUserComment).isTrue();
        assertThat(hasAnotherUserComment).isTrue();
    }

    @Test
    void 댓글_최대길이_경계값_테스트() {
        // given
        String maxLengthComment = "a".repeat(500); // 정확히 500자

        // when & then - 500자는 성공해야 함
        CommentResponse response = commentService.createComment(
                testUser.getId(), testBoard.getBoardId(), maxLengthComment);

        assertThat(response.commentId()).isNotNull();

        Comment savedComment = commentRepository.findById(response.commentId()).orElse(null);
        assertThat(savedComment.getCommentContent()).hasSize(500);
    }
}
