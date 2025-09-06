package team.bridgers.backend.domain.studytodo.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record StudyTodoSummaryListResponse(

        List<StudyTodoDetailResponse> studyTodoDetailResponses
) {
}
