package team.bridgers.backend.domain.board.domain;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.global.domain.BaseTimeEntity;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId; // 댓글 ID

    @Column(nullable = false, length = 500)
    private String commentContent;

    @Column(nullable = false)
    private String commentUserNickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Builder
    private Comment(String commentContent, String commentUserNickname, Board board) {
        this.commentContent = commentContent;
        this.commentUserNickname = commentUserNickname;
        this.board = board;
    }
}
