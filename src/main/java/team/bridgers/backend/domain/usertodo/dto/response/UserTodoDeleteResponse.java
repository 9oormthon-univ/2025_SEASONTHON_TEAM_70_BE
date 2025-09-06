package team.bridgers.backend.domain.usertodo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "할 일 삭제 응답 DTO")
public record UserTodoDeleteResponse(

        @Schema(description = "삭제된 할 일 ID", example = "1")
        Long userTodoId

) { }
