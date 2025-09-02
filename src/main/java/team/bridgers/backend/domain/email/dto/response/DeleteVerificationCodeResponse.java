package team.bridgers.backend.domain.email.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@NotBlank
@Builder
public record DeleteVerificationCodeResponse(
        Long Id
) {
}
