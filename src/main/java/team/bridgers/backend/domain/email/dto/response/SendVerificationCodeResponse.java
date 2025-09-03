package team.bridgers.backend.domain.email.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "인증 코드 발송 응답")
public record SendVerificationCodeResponse(

        @Schema(description = "인증 요청 고유 ID", example = "1")
        Long Id

) {}
