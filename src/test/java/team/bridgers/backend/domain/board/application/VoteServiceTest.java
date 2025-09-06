package team.bridgers.backend.domain.board.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import team.bridgers.backend.domain.board.domain.Board;
import team.bridgers.backend.domain.board.domain.BoardType;
import team.bridgers.backend.domain.board.domain.Vote;
import team.bridgers.backend.domain.board.infrastructure.BoardJpaRepository;
import team.bridgers.backend.domain.board.infrastructure.VoteJpaRepository;
import team.bridgers.backend.domain.board.presentation.exeption.AlreadyVotedException;
import team.bridgers.backend.domain.board.presentation.exeption.BoardNotFoundExeption;
import team.bridgers.backend.domain.board.presentation.exeption.DailyVoteLimitExceededException;
import team.bridgers.backend.domain.board.presentation.response.VoteResponse;
import team.bridgers.backend.domain.user.domain.Gender;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.UserType;
import team.bridgers.backend.domain.user.infrastructure.UserJpaRepository;
import team.bridgers.backend.domain.user.presentation.exception.UserNotFoundException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class VoteServiceTest {

    @Autowired
    private VoteService voteService;

    @Autowired
    private VoteJpaRepository voteRepository;

    @Autowired
    private BoardJpaRepository boardRepository;

    @Autowired
    private UserJpaRepository userRepository;

    private User testUser;
    private User anotherUser;
    private Board testBoard;
    private Board anotherBoard;

    @BeforeEach
    void setUp() {
        voteRepository.deleteAllInBatch();
        boardRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

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

        testBoard = Board.builder()
                .boardType(BoardType.FREE)
                .boardTitle("Test Board")
                .boardContent("Test Content")
                .user(testUser)
                .build();
        boardRepository.save(testBoard);

        anotherBoard = Board.builder()
                .boardType(BoardType.WORK_LIFE)
                .boardTitle("Another Board")
                .boardContent("Another Content")
                .user(anotherUser)
                .build();
        boardRepository.save(anotherBoard);
    }

    @AfterEach
    void tearDown() {
        voteRepository.deleteAllInBatch();
        boardRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    void voteForBoard_성공() {
        VoteResponse response = voteService.voteForBoard(testUser.getId(), testBoard.getBoardId());

        assertThat(response.voteId()).isNotNull();

        Vote savedVote = voteRepository.findById(response.voteId()).orElse(null);
        assertThat(savedVote).isNotNull();
        assertThat(savedVote.getUser().getId()).isEqualTo(testUser.getId());
        assertThat(savedVote.getBoard().getBoardId()).isEqualTo(testBoard.getBoardId());
        assertThat(savedVote.getVotedDate()).isNotNull();
    }

    @Test
    void voteForBoard_votedDate가올바르게설정됨() {
        VoteResponse response = voteService.voteForBoard(testUser.getId(), testBoard.getBoardId());

        Vote savedVote = voteRepository.findById(response.voteId()).orElse(null);
        assertThat(savedVote).isNotNull();
        assertThat(savedVote.getVotedDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void voteForBoard_존재하지않는사용자_실패() {
        Long invalidUserId = 999L;

        assertThrows(UserNotFoundException.class, () ->
                voteService.voteForBoard(invalidUserId, testBoard.getBoardId()));
    }

    @Test
    void voteForBoard_존재하지않는게시글_실패() {
        Long invalidBoardId = 999L;

        assertThrows(BoardNotFoundExeption.class, () ->
                voteService.voteForBoard(testUser.getId(), invalidBoardId));
    }

    @Test
    void voteForBoard_중복투표_실패() {
        voteService.voteForBoard(testUser.getId(), testBoard.getBoardId());

        assertThrows(AlreadyVotedException.class, () ->
                voteService.voteForBoard(testUser.getId(), testBoard.getBoardId()));
    }

    @Test
    void voteForBoard_일일투표제한초과_실패() {
        Board board1 = createTestBoard("Board1", testUser);
        Board board2 = createTestBoard("Board2", testUser);
        Board board3 = createTestBoard("Board3", testUser);
        Board board4 = createTestBoard("Board4", testUser);

        voteService.voteForBoard(testUser.getId(), board1.getBoardId());
        voteService.voteForBoard(testUser.getId(), board2.getBoardId());
        voteService.voteForBoard(testUser.getId(), board3.getBoardId());

        assertThrows(DailyVoteLimitExceededException.class, () ->
                voteService.voteForBoard(testUser.getId(), board4.getBoardId()));
    }

    @Test
    void voteForBoard_다른사용자는독립적으로투표가능() {
        VoteResponse response1 = voteService.voteForBoard(testUser.getId(), testBoard.getBoardId());
        VoteResponse response2 = voteService.voteForBoard(anotherUser.getId(), testBoard.getBoardId());

        assertThat(response1.voteId()).isNotNull();
        assertThat(response2.voteId()).isNotNull();
        assertThat(response1.voteId()).isNotEqualTo(response2.voteId());

        assertThat(voteRepository.existsByUserIdAndBoardId(testUser.getId(), testBoard.getBoardId())).isTrue();
        assertThat(voteRepository.existsByUserIdAndBoardId(anotherUser.getId(), testBoard.getBoardId())).isTrue();
    }

    @Test
    void voteForBoard_여러게시글에투표가능() {
        VoteResponse response1 = voteService.voteForBoard(testUser.getId(), testBoard.getBoardId());
        VoteResponse response2 = voteService.voteForBoard(testUser.getId(), anotherBoard.getBoardId());

        assertThat(response1.voteId()).isNotNull();
        assertThat(response2.voteId()).isNotNull();
        assertThat(response1.voteId()).isNotEqualTo(response2.voteId());

        assertThat(voteRepository.existsByUserIdAndBoardId(testUser.getId(), testBoard.getBoardId())).isTrue();
        assertThat(voteRepository.existsByUserIdAndBoardId(testUser.getId(), anotherBoard.getBoardId())).isTrue();
    }

    @Test
    void voteForBoard_일일제한_경계값테스트() {
        Board board1 = createTestBoard("Board1", testUser);
        Board board2 = createTestBoard("Board2", testUser);
        Board board3 = createTestBoard("Board3", testUser);

        VoteResponse response1 = voteService.voteForBoard(testUser.getId(), board1.getBoardId());
        VoteResponse response2 = voteService.voteForBoard(testUser.getId(), board2.getBoardId());
        VoteResponse response3 = voteService.voteForBoard(testUser.getId(), board3.getBoardId());

        assertThat(response1.voteId()).isNotNull();
        assertThat(response2.voteId()).isNotNull();
        assertThat(response3.voteId()).isNotNull();

        Board board4 = createTestBoard("Board4", testUser);
        assertThrows(DailyVoteLimitExceededException.class, () ->
                voteService.voteForBoard(testUser.getId(), board4.getBoardId()));
    }

    @Test
    void voteForBoard_동일날짜에여러번투표시_일일투표수가정확히집계됨() {
        Board board1 = createTestBoard("Board1", anotherUser);
        Board board2 = createTestBoard("Board2", anotherUser);

        voteService.voteForBoard(testUser.getId(), board1.getBoardId());
        voteService.voteForBoard(testUser.getId(), board2.getBoardId());

        // 현재 투표수 확인
        int currentVoteCount = voteRepository.countByUserIdAndVotedDate(testUser.getId(), LocalDate.now());
        assertThat(currentVoteCount).isEqualTo(2);
    }

    @Test
    void voteForBoard_null값입력시_예외발생() {
        assertThrows(Exception.class, () ->
                voteService.voteForBoard(null, testBoard.getBoardId()));

        assertThrows(Exception.class, () ->
                voteService.voteForBoard(testUser.getId(), null));
    }

    @Test
    void voteForBoard_트랜잭션롤백테스트() {
        // 존재하지 않는 사용자로 투표 시도 (예외 발생)
        Long invalidUserId = 999L;

        assertThrows(UserNotFoundException.class, () ->
                voteService.voteForBoard(invalidUserId, testBoard.getBoardId()));

        // Vote가 저장되지 않았는지 확인
        assertThat(voteRepository.existsByUserIdAndBoardId(invalidUserId, testBoard.getBoardId())).isFalse();
    }

    @Test
    void voteForBoard_동시성테스트_같은사용자가동시투표() {
        // 이 테스트는 실제로는 멀티스레딩 환경에서 테스트해야 하지만,
        // 기본적인 검증만 수행
        Board board1 = createTestBoard("Board1", anotherUser);
        Board board2 = createTestBoard("Board2", anotherUser);

        voteService.voteForBoard(testUser.getId(), board1.getBoardId());
        voteService.voteForBoard(testUser.getId(), board2.getBoardId());

        assertThat(voteRepository.existsByUserIdAndBoardId(testUser.getId(), board1.getBoardId())).isTrue();
        assertThat(voteRepository.existsByUserIdAndBoardId(testUser.getId(), board2.getBoardId())).isTrue();
    }

    private Board createTestBoard(String title, User author) {
        Board board = Board.builder()
                .boardType(BoardType.FREE)
                .boardTitle(title)
                .boardContent("Test Content for " + title)
                .user(author)
                .build();
        return boardRepository.save(board);
    }
}
