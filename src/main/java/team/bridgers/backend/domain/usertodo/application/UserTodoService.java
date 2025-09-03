package team.bridgers.backend.domain.usertodo.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.UserRepository;
import team.bridgers.backend.domain.usertodo.domain.Priority;
import team.bridgers.backend.domain.usertodo.domain.UserTodo;
import team.bridgers.backend.domain.usertodo.domain.UserTodoRepository;
import team.bridgers.backend.domain.usertodo.dto.request.UserTodoSaveRequest;
import team.bridgers.backend.domain.usertodo.dto.response.UserTodoSaveResponse;

@RequiredArgsConstructor
@Service
public class UserTodoService {

    private final UserTodoRepository userTodoRepository;
    private final UserRepository userRepository;

    @Transactional
    public UserTodoSaveResponse saveUserTodo(Long userId, UserTodoSaveRequest request) {
        User user = userRepository.findById(userId);

        UserTodo userTodo = UserTodo.builder()
                .user(user)
                .task(request.task())
                .deadLine(request.deadLine())
                .priority(Priority.valueOf(request.priority()))
                .build();
        userTodoRepository.save(userTodo);

        return UserTodoSaveResponse.builder()
                .userTodoId(userTodo.getId())
                .build();
    }

}
