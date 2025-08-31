package team.bridgers.backend.domain.user.application;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.UserType;
import team.bridgers.backend.domain.user.domain.VerificationCode;
import team.bridgers.backend.domain.user.infrastructure.UserRepository;
import team.bridgers.backend.domain.user.infrastructure.VerificationCodeRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final VerificationCodeRepository verificationCodeRepository;

    public void signUp(String userId, String nickname, String email, String password, String gender, String birthday, String type) {
        if(userRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("중복되는 아이디입니다.");
        }
        if(userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("중복되는 닉네임입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = User.builder()
                .userId(userId)
                .email(email)
                .nickname(nickname)
                .password(encodedPassword)
                .gender(gender)
                .birthday(birthday)
                .type(type)
                .build();
        userRepository.save(user);
    }

    public boolean needsVerificationEmail(String type) {
        return type.equals(UserType.STUDENT.toString()) || type.equals(UserType.WORKER.toString());
    }
}
