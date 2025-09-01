package team.bridgers.backend.domain.email.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.bridgers.backend.global.domain.BaseTimeEntity;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "verificationCode")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerificationCode extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    LocalDateTime expireTime;

    @Builder
    public VerificationCode(String email, String code) {
        this.email = email;
        this.code = code;
    }
}
