package team.bridgers.backend.domain.capsule.dto.request;

import lombok.Builder;

@Builder
public record ChangeCapsuleVisibilityRequest(
        Long capsuleId
) {
}
