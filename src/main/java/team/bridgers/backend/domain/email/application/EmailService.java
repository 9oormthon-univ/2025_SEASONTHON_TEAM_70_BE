package team.bridgers.backend.domain.email.application;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import team.bridgers.backend.domain.email.domain.VerificationCode;
import team.bridgers.backend.domain.email.frastructure.VerificationCodeRepository;
import team.bridgers.backend.domain.email.presentation.response.DeleteVerificationCodeResponse;
import team.bridgers.backend.domain.email.presentation.response.SendVerificationCodeResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final VerificationCodeRepository verificationCodeRepository;

    public String generateVerificationCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    public void sendVerificationCode(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Bridgers 이메일 인증");
        message.setText("인증코드는 " + code + "입니다.");
        javaMailSender.send(message);
    }

    public SendVerificationCodeResponse saveVerificationCode(String email) {
        String code = generateVerificationCode();
        sendVerificationCode(email, code);
        VerificationCode verificationCode = VerificationCode.builder()
                .email(email)
                .code(code)
                .build();
        verificationCodeRepository.save(verificationCode);

        Optional<VerificationCode> savedVerificationCode = verificationCodeRepository.findByEmail(email);

        return SendVerificationCodeResponse.builder()
                .Id(savedVerificationCode.get().getId())
                .build();
    }

    public boolean verifyCode(String email, String code) {

        Optional<VerificationCode> verificationCode = verificationCodeRepository.findByEmail(email);
        return verificationCode.get().getCode().equals(code);

    }

    public DeleteVerificationCodeResponse deleteVerificationCode(String email) {

        Optional<VerificationCode> verificationCode = verificationCodeRepository.findByEmail(email);
        verificationCodeRepository.delete(verificationCode.get());

        return DeleteVerificationCodeResponse.builder()
                .Id(verificationCode.get().getId())
                .build();
    }

}
