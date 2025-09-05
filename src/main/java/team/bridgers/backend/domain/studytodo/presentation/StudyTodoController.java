package team.bridgers.backend.domain.studytodo.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.bridgers.backend.domain.studytodo.application.StudyTodoService;
import team.bridgers.backend.domain.studytodo.dto.request.*;
import team.bridgers.backend.domain.studytodo.dto.response.*;
import team.bridgers.backend.domain.usertodo.dto.response.CompletedTodoStatsResponse;
import team.bridgers.backend.global.annotation.MemberId;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/study-todos")
@RestController
public class StudyTodoController {

    private final StudyTodoService studyTodoService;

    @PostMapping
    public ResponseEntity<CreateStudyTodoResponse> saveStudyTodo(@MemberId Long userId, @Valid @RequestBody CreateStudyTodoRequest request) {
        CreateStudyTodoResponse response = studyTodoService.saveStudyTodo(userId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{studyTodoId}/completed")
    public ResponseEntity<UpdateStudyTodoCompleteResponse> toggleCompleted(@RequestBody UpdateStudyTodoCompleteRequest request, @PathVariable("studyTodoId") Long studyTodoId) {
        UpdateStudyTodoCompleteResponse response = studyTodoService.toggleCompleted(request, studyTodoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<StudyTodoSummaryListResponse> getAllStudyTodos(@RequestBody StudyTodoGetRequest request, @RequestParam(defaultValue = "") String sortBy) {
        StudyTodoSummaryListResponse response = studyTodoService.getAllStudyTodos((request.studyTodoId()), sortBy);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{studyTodoId}")
    public ResponseEntity<UpdateStudyTodoResponse> updateStudyTodo(@PathVariable(name = "studyTodoId") Long studyTodoId, @RequestBody UpdateStudyTodoRequest request) {
        UpdateStudyTodoResponse response = studyTodoService.updateStudyTodo(studyTodoId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{studyTodoId}")
    public ResponseEntity<DeleteStudyTodoResponse> deleteStudyTodo(
            @RequestBody DeleteStudyTodoRequest request,
            @PathVariable("studyTodoId") Long studyTodoId
    ) {
        DeleteStudyTodoResponse response = studyTodoService.deleteStudyTodo(request.userStudyGroupId(), studyTodoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<CompletedTodoStatsResponse>> getCompletedStats(
            @RequestBody StudyTodoGetCompletedStatRequest request,
            @RequestParam String period
    ) {
        List<CompletedTodoStatsResponse> responses = studyTodoService.getCompletedStats(request.userStudyGroup(), period);
        return ResponseEntity.ok(responses);
    }
}
