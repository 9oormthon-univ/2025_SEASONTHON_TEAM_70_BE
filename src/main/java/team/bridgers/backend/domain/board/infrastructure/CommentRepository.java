package team.bridgers.backend.domain.board.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import team.bridgers.backend.domain.board.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
