package team.bridgers.backend.domain.studytodo.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StudyTodoDetailResponse(

        Long studyTodoId,

        String task,

        LocalDate deadLine,

        String priority,

        boolean completed
) {
}
