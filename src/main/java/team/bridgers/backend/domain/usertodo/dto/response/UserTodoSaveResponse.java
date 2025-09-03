package team.bridgers.backend.domain.usertodo.dto.response;

import lombok.Builder;

@Builder
public record UserTodoSaveResponse(

        Long userTodoId

) {
}
