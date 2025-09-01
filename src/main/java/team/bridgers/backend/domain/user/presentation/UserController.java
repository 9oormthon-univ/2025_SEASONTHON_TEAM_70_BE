package team.bridgers.backend.domain.user.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.bridgers.backend.domain.user.application.UserService;
import team.bridgers.backend.domain.user.presentation.request.SignUpRequest;
import team.bridgers.backend.domain.user.presentation.response.SignUpResponse;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController{

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        if(userService.needsVerificationEmail(request.type())){
            return ResponseEntity.status(202).body("이메일 인증이 필요합니다.");
        }

        SignUpResponse response = userService.signUp(
                request.loginId(), request.nickname(), request.email(),
                request.password(), request.confirmPassword(), request.gender(),
                request.birthday(), request.type(), request.interest()
        );

        return ResponseEntity.ok(response);
    }


}
