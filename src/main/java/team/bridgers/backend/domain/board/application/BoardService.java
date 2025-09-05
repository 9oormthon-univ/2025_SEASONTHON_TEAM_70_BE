package team.bridgers.backend.domain.board.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.bridgers.backend.domain.board.domain.Board;
import team.bridgers.backend.domain.board.domain.BoardRepository;
import team.bridgers.backend.domain.board.domain.BoardType;
import team.bridgers.backend.domain.board.infrastructure.BoardRepositoryImpl;
import team.bridgers.backend.domain.board.presentation.exeption.BoardUnauthorizedAccessExeption;
import team.bridgers.backend.domain.board.presentation.response.BoardDetailResponse;
import team.bridgers.backend.domain.board.presentation.response.BoardPageResponse;
import team.bridgers.backend.domain.board.presentation.response.BoardResponse;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.UserRepository;
import team.bridgers.backend.domain.user.infrastructure.UserRepositoryImpl;
import team.bridgers.backend.domain.user.presentation.exception.UserNotFoundException;


@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public BoardResponse createBoard(BoardType boardType, Long userId, String boardTitle, String boardContent) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);

        Board board = Board.builder()
                .boardType(boardType)
                .user(user)
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .build();
        boardRepository.save(board);

        return BoardResponse.builder()
                .boardId(board.getBoardId())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<BoardPageResponse> getBoards(BoardType boardType, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return boardRepository.findAllByBoardType(boardType, pageable)
                .map(board -> BoardPageResponse.builder()
                        .boardId(board.getBoardId())
                        .boardTitle(board.getBoardTitle())
                        .createdAt(board.getCreatedAt().toString())
                        .userNickname(board.getUser().getNickname())
                        .build());
    }

    @Transactional(readOnly = true)
    public Page<BoardPageResponse> searchBoards(String keyword, BoardType boardType, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return boardRepository.searchBoards(keyword, boardType, pageable)
                .map(board -> BoardPageResponse.builder()
                        .boardId(board.getBoardId())
                        .boardTitle(board.getBoardTitle())
                        .createdAt(board.getCreatedAt().toString())
                        .userNickname(board.getUser().getNickname())
                        .build());
    }

    @Transactional(readOnly = true)
    public BoardDetailResponse getBoard(Long id) {
        Board board = boardRepository.findByBoardId(id);
        return BoardDetailResponse.builder()
                .boardId(board.getBoardId())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .createdAt(board.getCreatedAt().toString())
                .updatedAt(board.getUpdatedAt().toString())
                .userNickname(board.getUser().getNickname())
                .build();
    }

    @Transactional
    public BoardResponse deleteBoard(Long id, Long userId) {
        Board board = boardRepository.findByBoardId(id);
        if (!board.getUser().getId().equals(userId)) {
            throw new BoardUnauthorizedAccessExeption();
        }
        boardRepository.delete(board);

        return  BoardResponse.builder()
                .boardId(board.getBoardId())
                .build();
    }

    @Transactional
    public BoardResponse updateBoard(Long id, Long userId, String boardTitle, String boardContent) {
        Board board = boardRepository.findByBoardId(id);
        if (!board.getUser().getId().equals(userId)) {
            throw new BoardUnauthorizedAccessExeption();
        }
        board.update(boardTitle, boardContent);

        return BoardResponse.builder()
                .boardId(board.getBoardId())
                .build();
    }
}
