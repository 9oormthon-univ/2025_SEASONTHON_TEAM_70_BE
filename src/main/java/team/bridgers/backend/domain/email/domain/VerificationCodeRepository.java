package team.bridgers.backend.domain.email.domain;

import java.util.Optional;

public interface VerificationCodeRepository {

    Optional<VerificationCode> findByEmail(String email);

    void save(VerificationCode verificationCode);

    void delete(VerificationCode verificationCode);

}
