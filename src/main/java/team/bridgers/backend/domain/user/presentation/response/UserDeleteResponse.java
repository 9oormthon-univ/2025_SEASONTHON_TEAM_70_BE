package team.bridgers.backend.domain.user.presentation.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@NotBlank
@Builder
public record UserDeleteResponse(
        Long Id
) {
}
