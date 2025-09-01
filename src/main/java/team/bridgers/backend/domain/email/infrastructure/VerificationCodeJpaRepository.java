package team.bridgers.backend.domain.email.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.bridgers.backend.domain.email.domain.VerificationCode;

import java.util.Optional;

@Repository
public interface VerificationCodeJpaRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByEmail(String email);
}
