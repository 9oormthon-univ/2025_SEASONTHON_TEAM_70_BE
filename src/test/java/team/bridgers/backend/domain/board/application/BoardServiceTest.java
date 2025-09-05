package team.bridgers.backend.domain.board.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
    @Autowired
    private TestRestTemplate restTemplate;

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
    void updateBoard_notFound() {
        assertThrows(BoardNotFoundExeption.class, () -> {
            boardService.updateBoard(999L, user.getId(), "newTitle", "newContent");
        });
    }

    @Test
    void updateBoard_unauthorized() {
        Board board = Board.builder()
                .boardType(BoardType.FREE)
                .boardTitle("title")
                .boardContent("content")
                .user(user)
                .build();
        boardRepository.save(board);

        assertThrows(BoardUnauthorizedAccessExeption.class, () -> {
            boardService.updateBoard(board.getBoardId(), anotherUser.getId(), "newTitle", "newContent");
        });
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
    void deleteBoard_notFound() {
        assertThrows(BoardNotFoundExeption.class, () -> {
            boardService.deleteBoard(999L, user.getId());
        });
    }

    @Test
    void deleteBoard_unauthorized() {
        Board board = Board.builder()
                .boardType(BoardType.FREE)
                .boardTitle("title")
                .boardContent("content")
                .user(user)
                .build();
        boardRepository.save(board);

        assertThrows(BoardUnauthorizedAccessExeption.class, () -> {
            boardService.deleteBoard(board.getBoardId(), anotherUser.getId());
        });
    }

    @Test
    void createBoard_differentBoardTypes() {
        BoardResponse freeBoard = boardService.createBoard(BoardType.FREE, user.getId(), "Free Title", "Free Content");
        BoardResponse workBoard = boardService.createBoard(BoardType.WORK_LIFE, user.getId(), "Work Title", "Work Content");
        BoardResponse jobBoard = boardService.createBoard(BoardType.JOB_POST, user.getId(), "Job Title", "Job Content");

        Board savedFree = boardRepository.findById(freeBoard.boardId()).orElse(null);
        Board savedWork = boardRepository.findById(workBoard.boardId()).orElse(null);
        Board savedJob = boardRepository.findById(jobBoard.boardId()).orElse(null);

        assertThat(savedFree.getBoardType()).isEqualTo(BoardType.FREE);
        assertThat(savedWork.getBoardType()).isEqualTo(BoardType.WORK_LIFE);
        assertThat(savedJob.getBoardType()).isEqualTo(BoardType.JOB_POST);
    }
}
