package team.bridgers.backend.domain.studytodo.dto.request;

import lombok.Builder;

@Builder
public record UpdateStudyTodoCompleteRequest(
        Long userStudyGroupId
) {
}
