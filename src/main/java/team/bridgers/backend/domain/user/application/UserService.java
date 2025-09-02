package team.bridgers.backend.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.bridgers.backend.domain.user.domain.Gender;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.UserRepository;
import team.bridgers.backend.domain.user.domain.UserType;
import team.bridgers.backend.domain.user.infrastructure.UserJpaRepository;
import team.bridgers.backend.domain.user.presentation.exception.DuplicateLoginIdException;
import team.bridgers.backend.domain.user.presentation.exception.DuplicateNicknameException;
import team.bridgers.backend.domain.user.presentation.exception.PasswordMismatchException;
import team.bridgers.backend.domain.user.presentation.exception.SignUpFailedException;
import team.bridgers.backend.domain.user.presentation.response.SignUpResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SignUpResponse signUp(String loginId, String nickname, String email, String password, String confirmPassword, Gender gender, String birthday, UserType type) {
        if(userRepository.existsByLoginId(loginId)) {
            throw new DuplicateLoginIdException();
        }
        if(userRepository.existsByNickname(nickname)) {
            throw new DuplicateNicknameException();
        }
        if(!password.equals(confirmPassword)) {
            throw new PasswordMismatchException();
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
        throw new SignUpFailedException();
    }

    public boolean needsVerificationEmail(UserType type) {
        return type == UserType.STUDENT|| type == UserType.WORKER;
    }
}
