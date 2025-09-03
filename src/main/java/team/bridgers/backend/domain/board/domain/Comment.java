package team.bridgers.backend.domain.board.domain;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.bridgers.backend.domain.user.domain.User;

@Getter
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId; // 댓글 ID

    @Column(nullable = false, length = 500)
    private String commentContent;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Board board; // 댓글이 달린 게시글

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 댓글 작성자

    @Builder
    public Comment(String commentContent, Board board, User user) {
        this.commentContent = commentContent;
        this.board = board;
        this.user = user;
    }

}
