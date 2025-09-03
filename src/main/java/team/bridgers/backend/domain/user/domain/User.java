package team.bridgers.backend.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import team.bridgers.backend.domain.user.presentation.exception.EmailOrPasswordNotInvalidException;
import team.bridgers.backend.global.domain.BaseTimeEntity;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String birthday;

    @Column(nullable = false)
    private UserType type;

    @Builder
    private User(String email, String nickname, String loginId, String password, Gender gender, String birthday, UserType type) {
        this.email = email;
        this.nickname = nickname;
        this.loginId = loginId;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.type = type;
    }

    public void checkPassword(PasswordEncoder passwordEncoder, String password) {
        if (!passwordEncoder.matches(password, this.password)) {
            throw new EmailOrPasswordNotInvalidException();
        }
    }

}
