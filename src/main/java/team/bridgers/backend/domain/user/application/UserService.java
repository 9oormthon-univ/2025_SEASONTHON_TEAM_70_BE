package team.bridgers.backend.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.bridgers.backend.domain.user.domain.Gender;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.UserRepository;
import team.bridgers.backend.domain.user.domain.UserType;
import team.bridgers.backend.domain.user.dto.request.SignUpRequest;
import team.bridgers.backend.domain.user.presentation.exception.DuplicateLoginIdException;
import team.bridgers.backend.domain.user.presentation.exception.DuplicateNicknameException;
import team.bridgers.backend.domain.user.presentation.exception.PasswordMismatchException;
import team.bridgers.backend.domain.user.presentation.exception.SignUpFailedException;
import team.bridgers.backend.domain.user.dto.response.SignUpResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SignUpResponse signUp(SignUpRequest request) {
        if(userRepository.existsByLoginId(request.loginId())) {
            throw new DuplicateLoginIdException();
        }
        if(userRepository.existsByNickname(request.nickname())) {
            throw new DuplicateNicknameException();
        }
        if(!request.password().equals(request.confirmPassword())) {
            throw new PasswordMismatchException();
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = User.builder()
                .loginId(request.loginId())
                .email(request.email())
                .nickname(request.nickname())
                .password(encodedPassword)
                .gender(request.gender())
                .birthday(request.birthday())
                .type(request.type())
                .build();

        userRepository.save(user);

        User savedUser = userRepository.findByEmail(request.email());
        if(savedUser != null) {
            return SignUpResponse.builder()
                    .Id(savedUser.getId())
                    .build();
        }
        throw new SignUpFailedException();
    }

    public boolean needsVerificationEmail(UserType type) {
        return type == UserType.STUDENT|| type == UserType.WORKER;
    }
}
