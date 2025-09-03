package team.bridgers.backend.domain.board.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import team.bridgers.backend.domain.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
