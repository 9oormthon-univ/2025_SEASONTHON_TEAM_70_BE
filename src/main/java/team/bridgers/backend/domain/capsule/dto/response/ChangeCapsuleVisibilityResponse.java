package team.bridgers.backend.domain.capsule.dto.response;

import lombok.Builder;
import team.bridgers.backend.domain.capsule.domain.Visibility;

@Builder
public record ChangeCapsuleVisibilityResponse(
        Long capsuleId,
        Visibility visibility
) {
}
