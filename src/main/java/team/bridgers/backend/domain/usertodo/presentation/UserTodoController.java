package team.bridgers.backend.domain.usertodo.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.bridgers.backend.domain.usertodo.application.UserTodoService;
import team.bridgers.backend.domain.usertodo.dto.request.UserTodoSaveRequest;
import team.bridgers.backend.domain.usertodo.dto.response.UserTodoSaveResponse;
import team.bridgers.backend.domain.usertodo.dto.response.UserTodoUpdateCompletedResponse;
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

}
