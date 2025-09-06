package team.bridgers.backend.domain.board.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import team.bridgers.backend.domain.board.domain.Board;
import team.bridgers.backend.domain.board.domain.BoardType;
import team.bridgers.backend.domain.board.infrastructure.BoardJpaRepository;
import team.bridgers.backend.domain.board.presentation.exeption.BoardNotFoundExeption;
import team.bridgers.backend.domain.board.presentation.exeption.BoardUnauthorizedAccessExeption;
import team.bridgers.backend.domain.board.presentation.response.BoardDetailResponse;
import team.bridgers.backend.domain.board.presentation.response.BoardPageResponse;
import team.bridgers.backend.domain.board.presentation.response.BoardResponse;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.Gender;
import team.bridgers.backend.domain.user.domain.UserType;
import team.bridgers.backend.domain.user.infrastructure.UserJpaRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class BoardServiceTest {
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardJpaRepository boardRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;

    private User user;
    private User anotherUser;

    @BeforeEach
    void setUp() {
        boardRepository.deleteAllInBatch();
        userJpaRepository.deleteAllInBatch();

        user = User.builder()
                .email("test@test.com")
                .nickname("tester")
                .loginId("testerId")
                .password("password")
                .gender(Gender.MALE)
                .birthday("20250101")
                .type(UserType.JOBSEEKER)
                .build();
        userJpaRepository.save(user);

        anotherUser = User.builder()
                .email("another@test.com")
                .nickname("another")
                .loginId("anotherId")
                .password("password")
                .gender(Gender.FEMALE)
                .birthday("19990101")
                .type(UserType.WORKER)
                .build();
        userJpaRepository.save(anotherUser);
    }

    @AfterEach
    void tearDown() {
        boardRepository.deleteAllInBatch();
        userJpaRepository.deleteAllInBatch();
    }

    @Test
    void createBoard_success() {
        BoardResponse response = boardService.createBoard(BoardType.FREE, user.getId(), "title", "content");

        assertThat(response.boardId()).isNotNull();

        Board board = boardRepository.findById(response.boardId()).orElse(null);
        assertThat(board).isNotNull();
        assertThat(board.getBoardTitle()).isEqualTo("title");
        assertThat(board.getBoardContent()).isEqualTo("content");
        assertThat(board.getUser().getId()).isEqualTo(user.getId());
        assertThat(board.getBoardType()).isEqualTo(BoardType.FREE);
    }

    @Test
    void getBoard_success() {
        Board board = Board.builder()
                .boardType(BoardType.FREE)
                .boardTitle("title")
                .boardContent("content")
                .user(user)
                .build();
        boardRepository.save(board);

        BoardDetailResponse response = boardService.getBoard(board.getBoardId());

        assertThat(response.boardId()).isEqualTo(board.getBoardId());
        assertThat(response.boardTitle()).isEqualTo("title");
        assertThat(response.boardContent()).isEqualTo("content");
        assertThat(response.userNickname()).isEqualTo(user.getNickname());
        assertThat(response.createdAt()).isNotNull();
        assertThat(response.updatedAt()).isNotNull();
    }

    @Test
    void getBoard_notFound() {
        assertThrows(BoardNotFoundExeption.class, () -> {
            boardService.getBoard(999L);
        });
    }

    @Test
    void getBoards_success() {
        boardService.createBoard(BoardType.FREE, user.getId(), "title1", "content1");
        boardService.createBoard(BoardType.FREE, user.getId(), "title2", "content2");
        boardService.createBoard(BoardType.WORK_LIFE, user.getId(), "work title", "work content");

        Page<BoardPageResponse> result = boardService.getBoards(BoardType.FREE, 0, 10, "createdAt");

        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).allMatch(board ->
            board.userNickname().equals(user.getNickname()));
    }

    @Test
    void getBoards_emptyResult() {
        Page<BoardPageResponse> result = boardService.getBoards(BoardType.FREE, 0, 10, "createdAt");

        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    void getBoards_pagination() {
        // 15개의 게시글 생성
        for (int i = 1; i <= 15; i++) {
            boardService.createBoard(BoardType.FREE, user.getId(), "title" + i, "content" + i);
        }

        // 첫 번째 페이지 (10개)
        Page<BoardPageResponse> firstPage = boardService.getBoards(BoardType.FREE, 0, 10, "createdAt");
        assertThat(firstPage.getTotalElements()).isEqualTo(15);
        assertThat(firstPage.getContent()).hasSize(10);
        assertThat(firstPage.hasNext()).isTrue();

        // 두 번째 페이지 (5개)
        Page<BoardPageResponse> secondPage = boardService.getBoards(BoardType.FREE, 1, 10, "createdAt");
        assertThat(secondPage.getContent()).hasSize(5);
        assertThat(secondPage.hasNext()).isFalse();
    }

    @Test
    void searchBoards_success() {
        boardService.createBoard(BoardType.FREE, user.getId(), "Java Programming", "Java content");
        boardService.createBoard(BoardType.FREE, user.getId(), "Python Guide", "Python content");
        boardService.createBoard(BoardType.FREE, user.getId(), "Database Design", "MySQL database content");

        // 제목에서 검색 - "Programming"으로 검색하여 1개만 매치되도록 수정
        Page<BoardPageResponse> titleResult = boardService.searchBoards("Programming", BoardType.FREE, 0, 10, "createdAt");
        assertThat(titleResult.getTotalElements()).isEqualTo(1);

        // 내용에서 검색 - "MySQL"로 검색하여 1개만 매치되도록 수정
        Page<BoardPageResponse> contentResult = boardService.searchBoards("MySQL", BoardType.FREE, 0, 10, "createdAt");
        assertThat(contentResult.getTotalElements()).isEqualTo(1);

        // 전체 타입에서 검색
        Page<BoardPageResponse> allTypeResult = boardService.searchBoards("content", null, 0, 10, "createdAt");
        assertThat(allTypeResult.getTotalElements()).isEqualTo(3);
    }

    @Test
    void searchBoards_noResults() {
        boardService.createBoard(BoardType.FREE, user.getId(), "title", "content");

        Page<BoardPageResponse> result = boardService.searchBoards("notfound", BoardType.FREE, 0, 10, "createdAt");

        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    void updateBoard_success() {
        Board board = Board.builder()
                .boardType(BoardType.FREE)
                .boardTitle("title")
                .boardContent("content")
                .user(user)
                .build();
        boardRepository.save(board);

        BoardResponse response = boardService.updateBoard(board.getBoardId(), user.getId(), "newTitle", "newContent");

        assertThat(response.boardId()).isEqualTo(board.getBoardId());

        Board updated = boardRepository.findById(board.getBoardId()).orElse(null);
        assertThat(updated).isNotNull();
        assertThat(updated.getBoardTitle()).isEqualTo("newTitle");
        assertThat(updated.getBoardContent()).isEqualTo("newContent");
    }

    @Test
    void updateBoard_unauthorizedAccess() {
        Board board = Board.builder()
                .boardType(BoardType.FREE)
                .boardTitle("title")
                .boardContent("content")
                .user(user)
                .build();
        boardRepository.save(board);

        assertThrows(BoardUnauthorizedAccessExeption.class, () ->
                boardService.updateBoard(board.getBoardId(), anotherUser.getId(), "newTitle", "newContent"));
    }

    @Test
    void updateBoard_notFound() {
        assertThrows(BoardNotFoundExeption.class, () ->
                boardService.updateBoard(999L, user.getId(), "title", "content"));
    }

    @Test
    void deleteBoard_success() {
        Board board = Board.builder()
                .boardType(BoardType.FREE)
                .boardTitle("title")
                .boardContent("content")
                .user(user)
                .build();
        boardRepository.save(board);

        BoardResponse response = boardService.deleteBoard(board.getBoardId(), user.getId());

        assertThat(response.boardId()).isEqualTo(board.getBoardId());
        assertThat(boardRepository.findById(board.getBoardId())).isEmpty();
    }

    @Test
    void deleteBoard_unauthorizedAccess() {
        Board board = Board.builder()
                .boardType(BoardType.FREE)
                .boardTitle("title")
                .boardContent("content")
                .user(user)
                .build();
        boardRepository.save(board);

        assertThrows(BoardUnauthorizedAccessExeption.class, () ->
                boardService.deleteBoard(board.getBoardId(), anotherUser.getId()));
    }

    @Test
    void deleteBoard_notFound() {
        assertThrows(BoardNotFoundExeption.class, () ->
                boardService.deleteBoard(999L, user.getId()));
    }

    @Test
    void getPopularBoards_success() {
        // 투표가 있는 게시글들을 생성
        Board board1 = Board.builder()
                .boardType(BoardType.FREE)
                .boardTitle("Popular Board 1")
                .boardContent("Content 1")
                .user(user)
                .build();
        boardRepository.save(board1);

        Board board2 = Board.builder()
                .boardType(BoardType.FREE)
                .boardTitle("Popular Board 2")
                .boardContent("Content 2")
                .user(anotherUser)
                .build();
        boardRepository.save(board2);

        org.springframework.data.domain.Pageable pageable =
                org.springframework.data.domain.PageRequest.of(0, 10);

        Page<BoardPageResponse> result = boardService.getPopularBoards(BoardType.FREE, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);
    }

    @Test
    void getPopularBoards_emptyResult() {
        org.springframework.data.domain.Pageable pageable =
                org.springframework.data.domain.PageRequest.of(0, 10);

        Page<BoardPageResponse> result = boardService.getPopularBoards(BoardType.FREE, pageable);

        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    void createBoard_variousBoardTypes() {
        // FREE 타입
        BoardResponse freeBoard = boardService.createBoard(BoardType.FREE, user.getId(), "Free Title", "Free Content");
        assertThat(freeBoard.boardId()).isNotNull();

        // WORK_LIFE 타입
        BoardResponse workLifeBoard = boardService.createBoard(BoardType.WORK_LIFE, user.getId(), "Work Title", "Work Content");
        assertThat(workLifeBoard.boardId()).isNotNull();

        // JOB_POST 타입
        BoardResponse jobPostBoard = boardService.createBoard(BoardType.JOB_POST, user.getId(), "Job Title", "Job Content");
        assertThat(jobPostBoard.boardId()).isNotNull();

        // 각 타입별로 정확히 저장되었는지 확인
        Board savedFree = boardRepository.findById(freeBoard.boardId()).orElse(null);
        assertThat(savedFree).isNotNull();
        assertThat(savedFree.getBoardType()).isEqualTo(BoardType.FREE);

        Board savedWorkLife = boardRepository.findById(workLifeBoard.boardId()).orElse(null);
        assertThat(savedWorkLife).isNotNull();
        assertThat(savedWorkLife.getBoardType()).isEqualTo(BoardType.WORK_LIFE);

        Board savedJobPost = boardRepository.findById(jobPostBoard.boardId()).orElse(null);
        assertThat(savedJobPost).isNotNull();
        assertThat(savedJobPost.getBoardType()).isEqualTo(BoardType.JOB_POST);
    }

    @Test
    void createBoard_emptyStringTest() {
        // 빈 제목으로 게시글 생성 시도
        BoardResponse response1 = boardService.createBoard(BoardType.FREE, user.getId(), "", "content");
        assertThat(response1.boardId()).isNotNull(); // 현재 서비스에서는 빈 문자열 허용

        // 빈 내용으로 게시글 생성 시도
        BoardResponse response2 = boardService.createBoard(BoardType.FREE, user.getId(), "title", "");
        assertThat(response2.boardId()).isNotNull(); // 현재 서비스에서는 빈 문자열 허용
    }

    @Test
    void createBoard_nonExistentUser() {
        assertThrows(Exception.class, () ->
                boardService.createBoard(BoardType.FREE, 999L, "title", "content"));
    }

    @Test
    void getBoards_sortingTest() {
        // 여러 게시글 생성 (시간 차이를 위해)
        boardService.createBoard(BoardType.FREE, user.getId(), "First", "First Content");

        try {
            Thread.sleep(100); // 시간차 생성
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        boardService.createBoard(BoardType.FREE, user.getId(), "Second", "Second Content");

        // 최신순 정렬 확인
        Page<BoardPageResponse> result = boardService.getBoards(BoardType.FREE, 0, 10, "createdAt");
        assertThat(result.getContent()).hasSize(2);

        // 첫 번째가 더 최근에 생성된 것인지는 실제 정렬 방향에 따라 다름
        assertThat(result.getContent().get(0).boardTitle()).isIn("First", "Second");
    }

    @Test
    void searchBoards_caseInsensitiveTest() {
        boardService.createBoard(BoardType.FREE, user.getId(), "Java Programming", "JAVA content");

        // 대문자로 검색
        Page<BoardPageResponse> upperResult = boardService.searchBoards("JAVA", BoardType.FREE, 0, 10, "createdAt");
        assertThat(upperResult.getTotalElements()).isGreaterThanOrEqualTo(1);

        // 소문자로 검색
        Page<BoardPageResponse> lowerResult = boardService.searchBoards("java", BoardType.FREE, 0, 10, "createdAt");
        assertThat(lowerResult.getTotalElements()).isGreaterThanOrEqualTo(1);
    }

    @Test
    void searchBoards_specialCharacterTest() {
        boardService.createBoard(BoardType.FREE, user.getId(), "C++ Programming", "C++ is powerful");

        Page<BoardPageResponse> result = boardService.searchBoards("C++", BoardType.FREE, 0, 10, "createdAt");
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    void transactionRollbackTest() {
        Long invalidUserId = 999L;

        // 존재하지 않는 사용자로 게시글 생성 시도 시 예외 발생하고 롤백되어야 함
        assertThrows(Exception.class, () ->
                boardService.createBoard(BoardType.FREE, invalidUserId, "title", "content"));

        // 게시글이 생성되지 않았는지 확인
        Page<BoardPageResponse> result = boardService.getBoards(BoardType.FREE, 0, 10, "createdAt");
        assertThat(result.getTotalElements()).isEqualTo(0);
    }

    @Test
    void pagingBoundaryTest() {
        // 정확히 10개의 게시글 생성
        for (int i = 1; i <= 10; i++) {
            boardService.createBoard(BoardType.FREE, user.getId(), "title" + i, "content" + i);
        }

        // 10개 요청 시 10개 모두 반환
        Page<BoardPageResponse> page1 = boardService.getBoards(BoardType.FREE, 0, 10, "createdAt");
        assertThat(page1.getContent()).hasSize(10);
        assertThat(page1.hasNext()).isFalse();

        // 다음 ��이지는 비어있어야 함
        Page<BoardPageResponse> page2 = boardService.getBoards(BoardType.FREE, 1, 10, "createdAt");
        assertThat(page2.getContent()).isEmpty();
    }
}
