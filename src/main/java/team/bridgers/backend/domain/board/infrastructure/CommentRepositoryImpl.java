package team.bridgers.backend.domain.board.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.bridgers.backend.domain.board.domain.Comment;
import team.bridgers.backend.domain.board.domain.CommentRepository;
import team.bridgers.backend.domain.board.presentation.exeption.CommentNotFoundException;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Comment findByCommentId(Long id) {
        return commentJpaRepository.findByCommentId(id).orElseThrow(CommentNotFoundException::new);
    }

    @Override
    public List<Comment> findAllByBoardId(Long id) {
        return commentJpaRepository.findAllByBoard_BoardId(id);
    }

    @Override
    public Comment save(Comment comment) {
        return commentJpaRepository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentJpaRepository.delete(comment);
    }
}
