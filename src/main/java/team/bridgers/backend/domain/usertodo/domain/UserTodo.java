package team.bridgers.backend.domain.usertodo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.global.domain.BaseTimeEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserTodo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String task;

    @Column(nullable = false)
    private LocalDate deadLine;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean completed = false;

    @Column
    private LocalDateTime completedAt;

    @Builder
    private UserTodo(User user, String task, LocalDate deadLine, Priority priority) {
        this.user = user;
        this.task = task;
        this.deadLine = deadLine;
        this.priority = priority;
    }

    public void complete() {
        this.completed = true;
        this.completedAt = LocalDateTime.now();
    }

    public void uncomplete() {
        this.completed = false;
        this.completedAt = null;
    }

    public void updateUserTodo(String task, LocalDate deadLine, Priority priority) {
        this.task = task;
        this.deadLine = deadLine;
        this.priority = priority;
    }

}
