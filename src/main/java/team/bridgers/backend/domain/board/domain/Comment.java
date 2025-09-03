package team.bridgers.backend.domain.board.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId; // 댓글 ID

    @Column(nullable = false)
    private String commentAuthor;  // 작성자

    @Column(nullable = false)
    private String commentContent;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Board board; // 댓글이 달린 게시글


}
