package team.bridgers.backend.domain.usertodo.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UserTodoSummaryListResponse(

        List<UserTodoDetailResponse> userTodoDetailResponses

) {
}
