package team.bridgers.backend.domain.studytodo.dto.response;

import lombok.Builder;

@Builder
public record DeleteStudyTodoResponse(

        Long studyTodoId

) {
}
