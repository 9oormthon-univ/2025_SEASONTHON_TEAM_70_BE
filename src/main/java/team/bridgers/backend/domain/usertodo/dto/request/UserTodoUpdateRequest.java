package team.bridgers.backend.domain.usertodo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "할 일 수정 요청 DTO")
public record UserTodoUpdateRequest(

        @Schema(description = "할 일 내용", example = "JPA 학습하기")
        @NotBlank(message = "할 일을 입력해 주세요.")
        String task,

        @Schema(description = "마감일", example = "2025-09-12")
        @NotNull(message = "마감일 설정을 해주세요.")
        LocalDate deadLine,

        @Schema(description = "우선 순위 (LOW, MEDIUM, HIGH)", example = "MEDIUM")
        @NotBlank(message = "우선순위를 설정해 주세요.")
        String priority

) { }
