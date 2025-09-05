package team.bridgers.backend.domain.usertodo.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.bridgers.backend.domain.usertodo.application.UserTodoService;
import team.bridgers.backend.domain.usertodo.dto.request.UserTodoSaveRequest;
import team.bridgers.backend.domain.usertodo.dto.request.UserTodoUpdateRequest;
import team.bridgers.backend.domain.usertodo.dto.response.*;
import team.bridgers.backend.global.annotation.MemberId;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/user-todos")
@RestController
public class UserTodoController {

    private final UserTodoService userTodoService;

    @PostMapping
    public ResponseEntity<UserTodoSaveResponse> saveUserTodo(
            @MemberId Long userId,
            @Valid @RequestBody UserTodoSaveRequest request
    ) {
        UserTodoSaveResponse response = userTodoService.saveUserTodo(userId, request);
        return ResponseEntity.status(CREATED).body(response);
    }

    @PatchMapping("/{userTodoId}/completed")
    public ResponseEntity<UserTodoUpdateCompletedResponse> toggleCompleted(
            @MemberId Long userId,
            @PathVariable(name = "userTodoId") Long userTodoId
    ) {
        UserTodoUpdateCompletedResponse response = userTodoService.toggleCompleted(userId, userTodoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<UserTodoSummaryListResponse> getAllUserTodos(
            @MemberId Long userId,
            @RequestParam(defaultValue = "") String sortBy
    ) {
        UserTodoSummaryListResponse response = userTodoService.getAllUserTodos(userId, sortBy);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userTodoId}")
    public ResponseEntity<UserTodoUpdateResponse> updateUserTodo(
            @MemberId Long userId,
            @PathVariable Long userTodoId,
            @Valid @RequestBody UserTodoUpdateRequest request
    ) {
        UserTodoUpdateResponse response = userTodoService.updateUserTodo(userId, userTodoId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userTodoId}")
    public ResponseEntity<UserTodoDeleteResponse> deleteUserTodo(
            @MemberId Long userId,
            @PathVariable Long userTodoId
    ) {
        UserTodoDeleteResponse response = userTodoService.deleteUserTodo(userId, userTodoId);
        return ResponseEntity.ok(response);
    }

}
