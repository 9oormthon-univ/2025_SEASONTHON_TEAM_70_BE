package team.bridgers.backend.domain.usertodo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserTodoDetailResponse(

        @Schema(description = "사용자 할 일 ID", example = "1")
        Long userTodoId,

        @Schema(description = "할 일 내용", example = "CS 공부하기")
        String task,

        @Schema(description = "마감 기한", example = "2025-09-10")
        LocalDate deadLine,

        @Schema(description = "우선순위", example = "HIGH")
        String priority,

        @Schema(description = "완료 여부", example = "false")
        boolean completed

) {
}
