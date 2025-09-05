package team.bridgers.backend.domain.board.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.bridgers.backend.domain.board.domain.Board;
import team.bridgers.backend.domain.board.domain.BoardRepository;
import team.bridgers.backend.domain.board.domain.Comment;
import team.bridgers.backend.domain.board.domain.CommentRepository;
import team.bridgers.backend.domain.board.infrastructure.BoardRepositoryImpl;
import team.bridgers.backend.domain.board.infrastructure.CommentRepositoryImpl;
import team.bridgers.backend.domain.board.presentation.exeption.ContentLengthExceeded;
import team.bridgers.backend.domain.board.presentation.exeption.CommentUnauthorizedAccessExeption;
import team.bridgers.backend.domain.board.presentation.response.CommentDetailResponse;
import team.bridgers.backend.domain.board.presentation.response.CommentResponse;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.UserRepository;
import team.bridgers.backend.domain.user.infrastructure.UserRepositoryImpl;
import team.bridgers.backend.domain.user.presentation.exception.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    private static final int MAX_COMMENT_LENGTH = 500;

    @Transactional
    public CommentResponse createComment(Long memberId, Long boardId, String commentContent) {
        validateCommentContent(commentContent);

        User user = userRepository.findById(memberId);
        Board board = boardRepository.findByBoardId(boardId);

        Comment comment = Comment.builder()
                .user(user)
                .board(board)
                .commentContent(commentContent)
                .build();
        commentRepository.save(comment);

        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .build();
    }

    @Transactional(readOnly = true)
    public List<CommentDetailResponse> getCommentsByBoard(Long boardId) {
        List<Comment> comments = commentRepository.findAllByBoardId(boardId);
        return comments.stream()
                .map(comment -> CommentDetailResponse.builder()
                        .commentId(comment.getCommentId())
                        .commentContent(comment.getCommentContent())
                        .createdAt(comment.getCreatedAt().toString())
                        .updatedAt(comment.getUpdatedAt().toString())
                        .userNickname(comment.getUser().getNickname())
                        .build())
                .toList();
    }

    @Transactional
    public CommentResponse updateComment(Long id, Long memberId, String commentContent) {
        validateCommentContent(commentContent);

        Comment comment = commentRepository.findByCommentId(id);

        if (!comment.getUser().getId().equals(memberId)) {
            throw new CommentUnauthorizedAccessExeption();
        }

        comment.updateCommentContent(commentContent);

        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .build();
    }

    @Transactional
    public CommentResponse deleteComment(Long id, Long memberId) {
        Comment comment = commentRepository.findByCommentId(id);

        if (!comment.getUser().getId().equals(memberId)) {
            throw new CommentUnauthorizedAccessExeption();
        }

        commentRepository.delete(comment);

        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .build();
    }

    private void validateCommentContent(String commentContent) {
        if (commentContent == null || commentContent.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용은 비어있을 수 없습니다.");
        }
        if (commentContent.length() > MAX_COMMENT_LENGTH) {
            throw new ContentLengthExceeded();
        }
    }
}
