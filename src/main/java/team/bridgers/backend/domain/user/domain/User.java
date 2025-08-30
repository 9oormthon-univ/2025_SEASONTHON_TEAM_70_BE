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
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String type;


    @Builder
    private User(String email, String userId, String password, String type) {
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.type = type;
    }
}
