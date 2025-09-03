package team.bridgers.backend.domain.capsule.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CapsuleInfoResponse(
        String title,
        String content,
        LocalDateTime createdAt
){
}
