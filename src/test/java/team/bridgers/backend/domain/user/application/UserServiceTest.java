package team.bridgers.backend.domain.user.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.springframework.security.crypto.password.PasswordEncoder;
import team.bridgers.backend.domain.email.application.EmailService;
import team.bridgers.backend.domain.email.infrastructure.VerificationCodeJpaRepository;
import team.bridgers.backend.domain.user.domain.Gender;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.UserType;
import team.bridgers.backend.domain.user.infrastructure.UserJpaRepository;
import team.bridgers.backend.domain.user.presentation.request.SignUpRequest;

import java.util.Optional;


@SpringBootTest
class UserServiceTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private VerificationCodeJpaRepository verificationCodeJpaRepository;

    @AfterEach
    void tearDown() {
        userJpaRepository.deleteAllInBatch();
    }

    @Test
    void signUpWithoutVerification() {
        SignUpRequest request = signUpRequest(UserType.JOBSEEKER);

        userService.signUp(
                request.loginId(), request.nickname(), request.email(), request.password(),
                request.confirmPassword(),request.gender(), request.birthday(), request.type()
        );

        Optional<User> user = userJpaRepository.findByEmail(request.email());

        assertThat(user.get().getEmail()).isEqualTo(request.email());
        assertThat(passwordEncoder.matches(request.password(),user.get().getPassword())).isTrue();

    }

    @Test
    void signUpWhenUserTypeIsWorker() {
        SignUpRequest request = signUpRequest(UserType.WORKER);

        userService.needsVerificationEmail(request.type());
    }




    @Test
    void needsVerificationEmail() {
        boolean resultForStudent = userService.needsVerificationEmail(UserType.STUDENT);
        boolean resultForWorker = userService.needsVerificationEmail(UserType.WORKER);

        assertThat(resultForStudent).isTrue();
        assertThat(resultForWorker).isTrue();

    }

    SignUpRequest signUpRequest(UserType type) {
        return SignUpRequest.builder()
                .email("bridgers@test.com")
                .nickname("bridge")
                .password("bridgers123")
                .confirmPassword("bridgers123")
                .loginId("bridgers123")
                .gender(Gender.MALE)
                .birthday("20250919")
                .type(type)
                .build();

    }
}