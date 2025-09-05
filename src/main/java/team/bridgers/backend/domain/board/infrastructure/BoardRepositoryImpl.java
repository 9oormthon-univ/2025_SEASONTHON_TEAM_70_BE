package team.bridgers.backend.domain.board.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import team.bridgers.backend.domain.board.domain.Board;
import team.bridgers.backend.domain.board.domain.BoardRepository;
import team.bridgers.backend.domain.board.domain.BoardType;
import team.bridgers.backend.domain.board.presentation.exeption.BoardNotFoundExeption;

@RequiredArgsConstructor
@Repository
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardJpaRepository boardJpaRepository;

    @Override
    public Board findByBoardId(Long id) {
        return boardJpaRepository.findByBoardId(id).orElseThrow(BoardNotFoundExeption::new);
    }

    @Override
    public void delete(Board board) {
        boardJpaRepository.delete(board);
    }

    @Override
    public Board save(Board board) {
        return boardJpaRepository.save(board);
    }

    @Override
    public Page<Board> findAllByBoardType(BoardType type, Pageable pageable) {
        return boardJpaRepository.findAllByBoardType(type, pageable);
    }

    @Override
    public Page<Board> searchBoards(String keyword, BoardType boardType, Pageable pageable) {
        if (boardType != null) {
            return boardJpaRepository.findByBoardTypeAndKeyword(boardType, keyword, pageable);
        } else {
            return boardJpaRepository.findByKeyword(keyword, pageable);
        }
    }
}
