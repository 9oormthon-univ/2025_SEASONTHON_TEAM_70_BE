package team.bridgers.backend.domain.email.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.bridgers.backend.domain.email.application.EmailService;
import team.bridgers.backend.domain.email.dto.request.SendVerificationCodeRequest;
import team.bridgers.backend.domain.email.dto.request.VerifyCodeRequest;
import team.bridgers.backend.domain.email.dto.response.DeleteVerificationCodeResponse;
import team.bridgers.backend.domain.email.dto.response.SendVerificationCodeResponse;


@RestController
@RequiredArgsConstructor
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<SendVerificationCodeResponse> sendVerificationCode(@RequestBody SendVerificationCodeRequest request) {
            SendVerificationCodeResponse response = emailService.saveVerificationCode(request.email());
            return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyEmail(@RequestBody VerifyCodeRequest request) {
        if(emailService.verifyCode(request.email(), request.code())) {

            DeleteVerificationCodeResponse response = emailService.deleteVerificationCode(request.email());
            return ResponseEntity.ok(response);

        }
        return ResponseEntity.badRequest().body("인증코드가 불일치합니다.");
    }

}
