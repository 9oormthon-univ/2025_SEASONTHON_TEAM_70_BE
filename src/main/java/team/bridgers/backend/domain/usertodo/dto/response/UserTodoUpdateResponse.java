package team.bridgers.backend.domain.usertodo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "할 일 수정 응답 DTO")
public record UserTodoUpdateResponse(

        @Schema(description = "수정된 할 일 ID", example = "1")
        Long userTodoId

) { }
