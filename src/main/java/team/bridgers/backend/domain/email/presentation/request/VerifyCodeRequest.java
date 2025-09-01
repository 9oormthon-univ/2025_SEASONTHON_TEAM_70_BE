package team.bridgers.backend.domain.email.presentation.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@NotBlank
@Builder
public record VerifyCodeRequest(
        String email,
        String code
) {
}
