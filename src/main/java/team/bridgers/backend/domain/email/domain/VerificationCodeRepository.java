package team.bridgers.backend.domain.email.domain;

import java.util.Optional;

public interface VerificationCodeRepository {

    VerificationCode findByEmail(String email);

    void save(VerificationCode verificationCode);

    void delete(VerificationCode verificationCode);

}
