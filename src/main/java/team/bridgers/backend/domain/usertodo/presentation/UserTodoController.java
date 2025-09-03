package team.bridgers.backend.domain.usertodo.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.bridgers.backend.domain.usertodo.application.UserTodoService;
import team.bridgers.backend.domain.usertodo.dto.request.UserTodoSaveRequest;
import team.bridgers.backend.domain.usertodo.dto.response.UserTodoSaveResponse;
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

}
