package team.bridgers.backend.domain.studytodo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateStudyTodoRequest(

        Long userStudyGroupId,

        @NotBlank(message = "할 일 테스트 작성해 주세요.")
        String task,

        @NotNull(message = "마감일 설정을 해주세요.")
        LocalDate deadLine,

        @NotBlank(message = "우선 순위 설정을 해주세요.")
        String priority

) {
}
