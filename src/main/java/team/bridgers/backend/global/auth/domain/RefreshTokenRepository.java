package team.bridgers.backend.global.auth.domain;

import java.util.Optional;

public interface RefreshTokenRepository {

    Optional<RefreshToken> findByToken(String token);

    void save(RefreshToken refreshToken);

    Optional<RefreshToken> findByUserId(Long id);

    void deleteByUserId(Long userId);

}
