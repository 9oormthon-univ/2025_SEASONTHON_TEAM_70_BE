package team.bridgers.backend.domain.board.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepository {

    Board findByBoardId(Long id);

    Board findByTitle(String title);

    Board findByContent(String content);

    Page<Board> findAllByBoardType(BoardType type, Pageable pageable);

    Board save(Board board);

    void delete(Board board);
}
