package team.bridgers.backend.domain.board.domain;

import java.util.List;

public interface CommentRepository {

    Comment findByCommentId(Long id);

    List<Comment> findAllByBoardId(Long id);

    Comment save(Comment comment);

    void delete(Comment comment);
}
