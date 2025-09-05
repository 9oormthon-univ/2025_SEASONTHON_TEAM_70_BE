package team.bridgers.backend.domain.board.domain;

import jakarta.persistence.*;
import lombok.*;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.global.domain.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoardType boardType;

    @Column(nullable = false, length = 100)
    private String boardTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String boardContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 관심도 투표 및 매칭 등 복수 Entity 연결
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Vote> votes = new ArrayList<>();

    public void addVotes(List<Vote> votes) {
        this.votes = votes;
    }

    @Builder
    private Board(BoardType boardType, String boardTitle, String boardContent, User user) {
        this.boardType = boardType;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.user = user;
    }

    public void update(String boardTitle, String boardContent) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }
}
