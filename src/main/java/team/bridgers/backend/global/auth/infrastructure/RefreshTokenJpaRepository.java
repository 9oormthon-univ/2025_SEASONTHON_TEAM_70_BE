package team.bridgers.backend.global.auth.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import team.bridgers.backend.global.auth.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findFirstByUserIdOrderByIdDesc(Long id);

    void deleteByUserId(Long memberId);

}
