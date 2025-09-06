package team.bridgers.backend.domain.study.dto.response;

import lombok.Builder;

@Builder
public record CreateStudyGroupResponse(
        Long groupId
) {
}
