package team.bridgers.backend.domain.study.dto.response;

import lombok.Builder;
import team.bridgers.backend.domain.study.domain.GroupType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record StudyGroupInfoResponse(
        String name,
        String content,
        int personnel,
        LocalDateTime createdAt,
        GroupType type
) {
}
