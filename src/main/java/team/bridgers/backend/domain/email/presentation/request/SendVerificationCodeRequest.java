package team.bridgers.backend.domain.email.presentation.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import team.bridgers.backend.domain.user.domain.UserType;

@NotBlank
@Builder
public record SendVerificationCodeRequest(
        String email
) {
}
