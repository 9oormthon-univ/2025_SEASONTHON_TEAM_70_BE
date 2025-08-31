package team.bridgers.backend.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import team.bridgers.backend.global.domain.BaseTimeEntity;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String birthday;

    @Column(nullable = false)
    private String type;

    private boolean emailVerified = false;

    @Builder
    private User(String email, String nickname, String userId, String password, String gender, String birthday, String type) {
        this.email = email;
        this.nickname = nickname;
        this.userId = userId;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.type = type;
    }

    public void verifyEmail() {
        this.emailVerified = true;
    }
}
