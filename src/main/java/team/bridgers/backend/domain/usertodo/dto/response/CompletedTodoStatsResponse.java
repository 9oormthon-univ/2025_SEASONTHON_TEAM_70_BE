package team.bridgers.backend.domain.usertodo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "기간별 완료된 할 일 통계 응답 DTO")
public record CompletedTodoStatsResponse(

        @Schema(description = "기간 (예: day, week, month)", example = "2025-09")
        String period,

        @Schema(description = "완료된 할 일 개수", example = "5")
        int completedCount

) { }
