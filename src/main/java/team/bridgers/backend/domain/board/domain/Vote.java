package team.bridgers.backend.domain.board.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.bridgers.backend.domain.user.domain.User;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "vote")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private LocalDate votedDate;

    @Builder
    public Vote(Board board, User user, LocalDate votedDate) {
        this.board = board;
        this.user = user;
        this.votedDate = votedDate;
    }
}