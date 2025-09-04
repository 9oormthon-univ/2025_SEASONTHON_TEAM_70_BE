package team.bridgers.backend.domain.board.application;

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
import team.bridgers.backend.domain.board.presentation.response.BoardDetailResponse;
import team.bridgers.backend.domain.board.presentation.response.BoardPageResponse;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.infrastructure.UserJpaRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class BoardServiceTest {
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardJpaRepository boardJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    private User user;

    @BeforeEach
    void setUp() {
        boardJpaRepository.deleteAll();
        userJpaRepository.deleteAllInBatch();
        user = User.builder()
                .email("test@test.com")
                .nickname("tester")
                .loginId("testerId")
                .password("password")
                .gender(team.bridgers.backend.domain.user.domain.Gender.MALE)
                .birthday("20250101")
                .type(team.bridgers.backend.domain.user.domain.UserType.JOBSEEKER)
                .build();
        userJpaRepository.save(user);
    }

    @Test
    void createBoard_success() {
        boardService.createBoard(BoardType.FREE, user.getId(), "title", "content");
        Board board = boardJpaRepository.findAll().get(0);
        assertThat(board.getBoardTitle()).isEqualTo("title");
        assertThat(board.getBoardContent()).isEqualTo("content");
        assertThat(board.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    void getBoard_success() {
        Board board = Board.builder()
                .boardType(BoardType.FREE)
                .boardTitle("title")
                .boardContent("content")
                .user(user)
                .build();
        boardJpaRepository.save(board);
        BoardDetailResponse response = boardService.getBoard(board.getBoardId());
        assertThat(response.boardId()).isEqualTo(board.getBoardId());
        assertThat(response.boardTitle()).isEqualTo("title");
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
        Page<BoardPageResponse> result = boardService.getBoards(BoardType.FREE, 0, 10, "createdAt");
        assertThat(result.getTotalElements()).isEqualTo(2);
    }

    @Test
    void updateBoard_success() {
        Board board = Board.builder()
                .boardType(BoardType.FREE)
                .boardTitle("title")
                .boardContent("content")
                .user(user)
                .build();
        boardJpaRepository.save(board);
        boardService.updateBoard(board.getBoardId(), user.getId(), "newTitle", "newContent");
        Board updated = boardJpaRepository.findById(board.getBoardId()).get();
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
    void deleteBoard_success() {
        Board board = Board.builder()
                .boardType(BoardType.FREE)
                .boardTitle("title")
                .boardContent("content")
                .user(user)
                .build();
        boardJpaRepository.save(board);
        boardService.deleteBoard(board.getBoardId(), user.getId());
        assertThat(boardJpaRepository.findById(board.getBoardId())).isEmpty();
    }

    @Test
    void deleteBoard_notFound() {
        assertThrows(BoardNotFoundExeption.class, () -> {
            boardService.deleteBoard(999L, user.getId());
        });
    }
}
