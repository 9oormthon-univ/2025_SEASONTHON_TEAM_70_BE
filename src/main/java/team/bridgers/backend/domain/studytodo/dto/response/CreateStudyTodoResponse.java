package team.bridgers.backend.domain.studytodo.dto.response;

import lombok.Builder;
import team.bridgers.backend.domain.study.domain.UserStudyGroup;
import team.bridgers.backend.domain.usertodo.domain.Priority;

import java.time.LocalDate;

@Builder
public record CreateStudyTodoResponse(
        Long studyTodoId
) {
}
