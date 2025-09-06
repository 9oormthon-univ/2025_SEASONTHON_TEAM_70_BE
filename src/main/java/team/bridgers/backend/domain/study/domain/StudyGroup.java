package team.bridgers.backend.domain.study.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.global.domain.BaseTimeEntity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "study_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyGroup extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    private String content;

    private int personnel;

    private GroupType type;

    @Builder
    private StudyGroup(String name, String content, GroupType type) {
        this.name = name;
        this.content = content;
        this.personnel = 1;
        this.type = type;
    }

    public void updatePersonnel() {
        this.personnel++;
    }

}
