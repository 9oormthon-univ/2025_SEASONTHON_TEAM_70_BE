package team.bridgers.backend.domain.study.dto.response;

import lombok.Builder;
import team.bridgers.backend.domain.study.domain.GroupType;

import java.time.LocalDate;

@Builder
public record StudyGroupInfoResponse(
        String name,
        String content,
        int personnel,
        LocalDate createdDate,
        GroupType type
) {
}
