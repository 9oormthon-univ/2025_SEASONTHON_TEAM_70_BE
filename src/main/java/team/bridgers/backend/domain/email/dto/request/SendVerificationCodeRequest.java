package team.bridgers.backend.domain.email.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
@Schema(description = "인증 코드 발송 요청")
public record SendVerificationCodeRequest(

        @NotBlank(message = "인증 이메일 주소를 입력해 주세요.")
        @Schema(description = "인증 이메일 주소", example = "user@example.com", requiredMode = REQUIRED)
        String email

) {}
