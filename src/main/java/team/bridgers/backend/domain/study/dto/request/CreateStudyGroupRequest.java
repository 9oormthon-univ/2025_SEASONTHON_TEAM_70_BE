package team.bridgers.backend.domain.study.dto.request;

import lombok.Builder;
import team.bridgers.backend.domain.study.domain.GroupType;

import java.time.LocalDate;

@Builder
public record CreateStudyGroupRequest(
        String name,
        String content,
        GroupType type

) {
}
