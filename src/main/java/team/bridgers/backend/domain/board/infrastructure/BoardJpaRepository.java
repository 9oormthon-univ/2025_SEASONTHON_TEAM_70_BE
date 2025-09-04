package team.bridgers.backend.domain.board.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import team.bridgers.backend.domain.board.domain.Board;
import team.bridgers.backend.domain.board.domain.BoardType;

import java.util.Optional;


public interface BoardJpaRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByBoardId(Long id);
    Optional<Board> findByBoardTitle(String title);
    Optional<Board> findByBoardContent(String content);
    Page<Board> findAllByBoardType(BoardType type, Pageable pageable);
}
