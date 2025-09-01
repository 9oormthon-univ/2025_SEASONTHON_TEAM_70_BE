package team.bridgers.backend.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.bridgers.backend.domain.user.domain.Gender;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.UserRepository;
import team.bridgers.backend.domain.user.domain.UserType;
import team.bridgers.backend.domain.user.infrastructure.UserJpaRepository;
import team.bridgers.backend.domain.user.presentation.response.SignUpResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SignUpResponse signUp(String loginId, String nickname, String email, String password, String confirmPassword, Gender gender, String birthday, UserType type) {
        if(userRepository.existsByLoginId(loginId)) {
            throw new IllegalArgumentException("중복되는 아이디입니다.");
        }
        if(userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("중복되는 닉네임입니다.");
        }
        if(!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = User.builder()
                .loginId(loginId)
                .email(email)
                .nickname(nickname)
                .password(encodedPassword)
                .gender(gender)
                .birthday(birthday)
                .type(type)
                .build();

        userRepository.save(user);

        Optional<User> savedUser = userRepository.findByEmail(email);
        if(savedUser.isPresent()) {
            return SignUpResponse.builder()
                    .Id(savedUser.get().getId())
                    .build();
        }
        throw new IllegalArgumentException("회원가입에 문제가 발생하였습니다.");
    }

    public boolean needsVerificationEmail(UserType type) {
        return type == UserType.STUDENT|| type == UserType.WORKER;
    }
}
