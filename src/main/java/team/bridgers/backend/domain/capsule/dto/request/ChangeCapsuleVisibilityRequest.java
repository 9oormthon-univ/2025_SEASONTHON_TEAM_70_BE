package team.bridgers.backend.domain.capsule.dto.request;

import lombok.Builder;
import team.bridgers.backend.domain.capsule.domain.Visibility;

@Builder
public record ChangeCapsuleVisibilityRequest(
        Visibility visibility
) {
}
