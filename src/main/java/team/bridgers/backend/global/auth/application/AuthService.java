package team.bridgers.backend.global.auth.application;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.UserRepository;
import team.bridgers.backend.domain.user.presentation.exception.UserNotFoundException;
import team.bridgers.backend.global.auth.dto.response.LoginResultResponse;
import team.bridgers.backend.global.jwt.injector.TokenInjector;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoginProcessor loginProcessor;
    private final TokenInjector tokenInjector;

    @Transactional
    public LoginResultResponse login(String loginId, String password, HttpServletResponse response) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(UserNotFoundException::new);
        user.checkPassword(passwordEncoder, password);

        LoginResultResponse result = loginProcessor.generateLoginResult(user.getId());

        tokenInjector.injectTokensToCookie(result, response);

        return loginProcessor.generateLoginResult(user.getId());
    }

}
