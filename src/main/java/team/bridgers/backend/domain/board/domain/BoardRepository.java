package team.bridgers.backend.domain.board.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository {

    Board findByBoardId(Long id);

    Page<Board> findAllByBoardType(BoardType type, Pageable pageable);

    Page<Board> searchBoards(String keyword, BoardType boardType, Pageable pageable);

    Board save(Board board);

    void delete(Board board);

    Page<Board> findPopularBoards(BoardType boardType, Pageable pageable);
}
