package team.bridgers.backend.domain.capsule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateCapsuleRequest(

        @NotBlank
        @Size(min = 1, max = 20)
        String title,

        @NotBlank
        @Size(min = 1, max = 300)
        String content
) {
}
