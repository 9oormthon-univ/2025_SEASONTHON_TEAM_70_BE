package team.bridgers.backend.domain.usertodo.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserTodoDetailResponse(

        Long userTodoId,

        String task,

        LocalDate deadLine,

        String priority,

        boolean completed

) {
}
