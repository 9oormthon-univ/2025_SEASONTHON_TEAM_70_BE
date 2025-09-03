package team.bridgers.backend.global.auth.application;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.bridgers.backend.global.auth.domain.RefreshToken;
import team.bridgers.backend.global.auth.domain.RefreshTokenRepository;
import team.bridgers.backend.global.auth.dto.response.LoginResultResponse;
import team.bridgers.backend.global.auth.presentation.exception.AuthenticationRequiredException;
import team.bridgers.backend.global.jwt.config.TokenProperties;
import team.bridgers.backend.global.jwt.generator.JwtTokenGenerator;
import team.bridgers.backend.global.jwt.injector.TokenInjector;
import team.bridgers.backend.global.jwt.resolver.JwtTokenResolver;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenGenerator tokenGenerator;
    private final JwtTokenResolver tokenResolver;
    private final TokenInjector tokenInjector;
    private final TokenProperties tokenProperties;

    @Transactional
    public LoginResultResponse reissueBasedOnRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = tokenResolver.resolveRefreshTokenFromRequest(request)
                .orElseThrow(AuthenticationRequiredException::new);

        RefreshToken savedRefreshToken = validateAndGetSavedRefreshToken(refreshToken);

        return getReissuedTokenResult(response, savedRefreshToken);
    }

    private LoginResultResponse getReissuedTokenResult(HttpServletResponse response, RefreshToken savedRefreshToken) {
        Long memberId = savedRefreshToken.getUserId();

        String reissuedAccessToken = tokenGenerator.generateAccessToken(memberId);
        String rotatedRefreshToken = this.rotate(savedRefreshToken);

        LoginResultResponse loginResult = new LoginResultResponse(
                reissuedAccessToken, rotatedRefreshToken);
        tokenInjector.injectTokensToCookie(loginResult, response);

        return loginResult;
    }

    private RefreshToken validateAndGetSavedRefreshToken(String refreshToken) {
        Long memberId = Long.valueOf(tokenResolver.getSubjectFromToken(refreshToken));
        RefreshToken savedRefreshToken = this.getByTokenString(refreshToken);

        savedRefreshToken.validateWith(refreshToken, memberId);

        return savedRefreshToken;
    }

    public RefreshToken getByTokenString(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(AuthenticationRequiredException::new);
    }

    private String rotate(RefreshToken refreshToken) {
        String reissuedToken = tokenGenerator.generateRefreshToken(refreshToken.getUserId());
        refreshToken.rotate(reissuedToken);
        refreshToken.updateExpirationIfExpired(tokenProperties.expirationTime().refreshToken());
        refreshTokenRepository.save(refreshToken);

        return reissuedToken;
    }

}
