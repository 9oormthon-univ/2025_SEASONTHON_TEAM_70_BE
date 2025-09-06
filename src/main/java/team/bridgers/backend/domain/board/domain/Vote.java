package team.bridgers.backend.domain.board.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.global.domain.BaseTimeEntity;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Vote extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column( nullable = false)
    private LocalDate votedDate;

    @Builder
    private Vote(User user, Board board, LocalDate votedDate) {
        this.user = user;
        this.board = board;
        this.votedDate = votedDate;
    }
}
