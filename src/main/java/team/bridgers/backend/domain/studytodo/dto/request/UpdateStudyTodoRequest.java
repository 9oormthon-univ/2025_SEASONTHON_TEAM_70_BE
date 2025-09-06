package team.bridgers.backend.domain.studytodo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateStudyTodoRequest(

        Long userStudyGroupId,

        @NotBlank(message = "할 일을 입력해 주세요.")
        String task,

        @NotNull(message = "마감일 설정을 해주세요.")
        LocalDate deadLine,

        @NotBlank(message = "우선순위를 설정해 주세요.")
        String priority
) {
}
