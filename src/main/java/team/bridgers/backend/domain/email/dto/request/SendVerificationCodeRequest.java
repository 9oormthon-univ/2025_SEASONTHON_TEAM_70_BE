package team.bridgers.backend.domain.email.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import team.bridgers.backend.domain.user.domain.UserType;

@NotBlank
@Builder
public record SendVerificationCodeRequest(
        String email
) {
}
