package team.bridgers.backend.domain.usertodo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "할 일 요약 리스트 응답 DTO (default: 최신순, priority: 우선순위순, deadline: 마감기한 순)")
public record UserTodoSummaryListResponse(

        @Schema(description = "할 일 상세 응답 리스트")
        List<UserTodoDetailResponse> userTodoDetailResponses

) { }
