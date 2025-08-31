package team.bridgers.backend.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import team.bridgers.backend.domain.user.domain.VerificationCode;
import team.bridgers.backend.domain.user.infrastructure.VerificationCodeRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final VerificationCodeRepository verificationCodeRepository;

    public String generateVerificationCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    public void sendVerificationCode(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Bridgers 이메일 인증");
        message.setText("인증코드는 " + code + "입니다.");
        mailSender.send(message);
    }

    public void saveVerificationCode(String email) {
        String code = generateVerificationCode();
        sendVerificationCode(email, code);
        VerificationCode verificationCode = VerificationCode.builder()
                .email(email)
                .code(code)
                .build();
        verificationCodeRepository.save(verificationCode);
    }

    public boolean verifyCode(String email, String code) {

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(email);
        return !verificationCode.getCode().equals(code);

    }

}
