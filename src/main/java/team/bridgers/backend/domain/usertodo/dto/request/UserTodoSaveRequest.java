package team.bridgers.backend.domain.usertodo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "할 일 저장 요청 DTO")
public record UserTodoSaveRequest(

        @Schema(description = "할 일 내용", example = "Spring Boot 공부하기")
        @NotBlank(message = "할 일 테스트 작성해 주세요.")
        String task,

        @Schema(description = "마감일", example = "2025-09-10")
        @NotNull(message = "마감일 설정을 해주세요.")
        LocalDate deadLine,

        @Schema(description = "우선 순위 (LOW, MEDIUM, HIGH)", example = "HIGH")
        @NotBlank(message = "우선 순위 설정을 해주세요.")
        String priority

) { }
