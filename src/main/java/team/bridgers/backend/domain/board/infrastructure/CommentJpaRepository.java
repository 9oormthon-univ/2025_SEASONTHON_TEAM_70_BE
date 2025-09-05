package team.bridgers.backend.domain.board.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import team.bridgers.backend.domain.board.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByCommentId(Long id);

    List<Comment> findAllByBoard_BoardId(Long id);

    void delete(Comment comment);
}
