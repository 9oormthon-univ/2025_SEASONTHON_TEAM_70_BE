package team.bridgers.backend.domain.study.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.bridgers.backend.domain.user.domain.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserStudyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyGroup studyGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    private UserStudyGroup(StudyGroup studyGroup, User user) {
        this.studyGroup = studyGroup;
        this.user = user;
    }
}
