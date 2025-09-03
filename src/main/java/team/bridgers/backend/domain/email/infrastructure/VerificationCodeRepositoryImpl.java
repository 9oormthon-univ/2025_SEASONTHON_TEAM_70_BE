package team.bridgers.backend.domain.email.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.bridgers.backend.domain.email.domain.VerificationCode;
import team.bridgers.backend.domain.email.domain.VerificationCodeRepository;
import team.bridgers.backend.domain.email.presentation.exception.VerificationCodeNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class VerificationCodeRepositoryImpl implements VerificationCodeRepository {

    private final VerificationCodeJpaRepository verificationCodeJpaRepository;

    @Override
    public VerificationCode findByEmail(String email) {

        Optional<VerificationCode> verificationCode = verificationCodeJpaRepository.findByEmail(email);

        if(verificationCode.isPresent()) {
            return verificationCode.get();
        }
        throw new VerificationCodeNotFoundException();
    }

    @Override
    public void save(VerificationCode verificationCode) {
        verificationCodeJpaRepository.save(verificationCode);
    }

    @Override
    public void delete(VerificationCode verificationCode) {
        verificationCodeJpaRepository.delete(verificationCode);
    }

}
