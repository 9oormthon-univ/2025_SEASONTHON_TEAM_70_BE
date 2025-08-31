package team.bridgers.backend.domain.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.bridgers.backend.domain.user.application.EmailService;
import team.bridgers.backend.domain.user.application.UserService;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.VerificationCode;
import team.bridgers.backend.domain.user.infrastructure.VerificationCodeRepository;
import team.bridgers.backend.domain.user.presentation.request.SignUpRequest;
import team.bridgers.backend.domain.user.presentation.request.VerificationCodeRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController{

    private final UserService userService;
    private final EmailService emailService;
    private final VerificationCodeRepository verificationCodeRepository;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest request) {
        if(userService.needsVerificationEmail(request.type())){
            emailService.saveVerificationCode(request.email());
            return ResponseEntity.status(202).body("이메일 인증이 필요합니다.");
        }
        userService.signUp(request.userId(), request.nickname(), request.email(), request.password(), request.gender(), request.birthday(), request.type());
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyEmail(@RequestBody VerificationCodeRequest request) {
        if(emailService.verifyCode(request.email(), request.code())) {
            return ResponseEntity.badRequest().body("인증 코드가 불일치합니다.");
        }
        userService.signUp();

    }
}
