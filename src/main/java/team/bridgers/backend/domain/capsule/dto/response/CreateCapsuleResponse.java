package team.bridgers.backend.domain.capsule.dto.response;

import lombok.Builder;

@Builder
public record CreateCapsuleResponse(
        Long capsuleId
) {
}
