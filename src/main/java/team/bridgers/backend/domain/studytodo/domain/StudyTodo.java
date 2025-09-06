package team.bridgers.backend.domain.studytodo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.bridgers.backend.domain.study.domain.UserStudyGroup;
import team.bridgers.backend.domain.usertodo.domain.Priority;
import team.bridgers.backend.global.domain.BaseTimeEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyTodo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_study_group_id")
    private UserStudyGroup userStudyGroup;

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
    private StudyTodo(UserStudyGroup userStudyGroup, String task, LocalDate deadLine, Priority priority) {
        this.userStudyGroup = userStudyGroup;
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

    public void updateGroupTodo(String task, LocalDate deadLine, Priority priority) {
        this.task = task;
        this.deadLine = deadLine;
        this.priority = priority;
    }
}
