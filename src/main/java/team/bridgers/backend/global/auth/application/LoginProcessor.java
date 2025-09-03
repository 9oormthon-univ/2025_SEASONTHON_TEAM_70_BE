package team.bridgers.backend.global.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.bridgers.backend.global.auth.domain.RefreshToken;
import team.bridgers.backend.global.auth.domain.RefreshTokenRepository;
import team.bridgers.backend.global.auth.dto.response.LoginResultResponse;
import team.bridgers.backend.global.jwt.config.TokenProperties;
import team.bridgers.backend.global.jwt.generator.JwtTokenGenerator;

@RequiredArgsConstructor
@Component
public class LoginProcessor {

    private final JwtTokenGenerator tokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProperties tokenProperties;

    public LoginResultResponse generateLoginResult(Long userId) {
        String accessToken = tokenGenerator.generateAccessToken(userId);
        String refreshToken = tokenGenerator.generateRefreshToken(userId);

        RefreshToken refreshTokenEntity = refreshTokenRepository.findByUserId(userId)
                .orElse(RefreshToken.of(userId, refreshToken, tokenProperties.expirationTime().refreshToken()));

        refreshTokenEntity.rotate(refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);

        return new LoginResultResponse(accessToken, refreshToken);
    }

}
