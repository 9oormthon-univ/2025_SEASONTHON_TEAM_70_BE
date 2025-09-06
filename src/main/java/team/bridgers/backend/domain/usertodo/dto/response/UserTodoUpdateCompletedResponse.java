package team.bridgers.backend.domain.usertodo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "할 일 완료 여부 변경 응답 DTO")
public record UserTodoUpdateCompletedResponse(

        @Schema(description = "할 일 ID", example = "1")
        Long userTodoId,

        @Schema(description = "완료 여부", example = "true")
        boolean completed

) { }
