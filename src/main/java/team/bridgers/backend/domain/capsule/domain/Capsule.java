package team.bridgers.backend.domain.capsule.domain;

import jakarta.persistence.*;
import lombok.*;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.global.domain.BaseTimeEntity;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "capsule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Capsule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    Visibility visibility;

    @Builder
    private Capsule(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.visibility = Visibility.HIDDEN;
    }

    public void updateCapsule() {
        this.visibility = Visibility.VISIBLE;
    }
}
