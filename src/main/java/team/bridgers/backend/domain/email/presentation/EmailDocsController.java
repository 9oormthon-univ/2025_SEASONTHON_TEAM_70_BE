package team.bridgers.backend.domain.email.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import team.bridgers.backend.domain.email.dto.request.SendVerificationCodeRequest;
import team.bridgers.backend.domain.email.dto.request.VerifyCodeRequest;
import team.bridgers.backend.domain.email.dto.response.SendVerificationCodeResponse;
import team.bridgers.backend.domain.email.dto.response.DeleteVerificationCodeResponse;

@Tag(name = "Email", description = "이메일 인증 API")
public interface EmailDocsController {

    @Operation(summary = "인증 코드 발송", description = "사용자 이메일로 인증 코드를 발송합니다.")
    @ApiResponse(responseCode = "200", description = "인증 코드 발송 성공",
            content = @Content(schema = @Schema(implementation = SendVerificationCodeResponse.class)))
    @PostMapping("/send")
    ResponseEntity<SendVerificationCodeResponse> sendVerificationCode(@RequestBody SendVerificationCodeRequest request);

    @Operation(summary = "인증 코드 검증", description = "이메일과 인증 코드를 검증합니다.")
    @ApiResponse(responseCode = "200", description = "인증코드 일치, 삭제 성공",
            content = @Content(schema = @Schema(implementation = DeleteVerificationCodeResponse.class)))
    @ApiResponse(responseCode = "400", description = "인증코드 불일치")
    @PostMapping("/verify-code")
    ResponseEntity<?> verifyEmail(@RequestBody VerifyCodeRequest request);

}
