package team.bridgers.backend.domain.usertodo.dto.response;

import lombok.Builder;

@Builder
public record CompletedTodoStatsResponse(

        String period,

        int completedCount

) {
}
