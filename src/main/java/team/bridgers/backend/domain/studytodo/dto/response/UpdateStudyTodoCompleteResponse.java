package team.bridgers.backend.domain.studytodo.dto.response;

import lombok.Builder;

@Builder
public record UpdateStudyTodoCompleteResponse(
        Long studyTodoId,
        boolean complete
) {
}
