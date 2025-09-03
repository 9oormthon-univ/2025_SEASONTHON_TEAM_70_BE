package team.bridgers.backend.domain.board.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import team.bridgers.backend.domain.board.domain.Board;
import team.bridgers.backend.domain.board.domain.BoardType;


public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByBoardType(BoardType type, Pageable pageable);
}
