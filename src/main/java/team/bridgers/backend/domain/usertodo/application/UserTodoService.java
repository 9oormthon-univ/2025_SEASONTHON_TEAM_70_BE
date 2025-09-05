package team.bridgers.backend.domain.usertodo.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.UserRepository;
import team.bridgers.backend.domain.usertodo.domain.Priority;
import team.bridgers.backend.domain.usertodo.domain.UserTodo;
import team.bridgers.backend.domain.usertodo.domain.UserTodoRepository;
import team.bridgers.backend.domain.usertodo.dto.request.UserTodoSaveRequest;
import team.bridgers.backend.domain.usertodo.dto.request.UserTodoUpdateRequest;
import team.bridgers.backend.domain.usertodo.dto.response.*;

import java.time.LocalDate;
import java.util.List;

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

    @Transactional
    public UserTodoUpdateCompletedResponse toggleCompleted(
            Long userId,
            Long userTodoId
    ) {
        userRepository.findById(userId);

        UserTodo userTodo = userTodoRepository.findByUserTodoId(userTodoId);

        if (userTodo.isCompleted()) {
            userTodo.uncomplete();
        } else {
            userTodo.complete();
        }

        return UserTodoUpdateCompletedResponse.builder()
                .userTodoId(userTodo.getId())
                .completed(userTodo.isCompleted())
                .build();
    }

    @Transactional(readOnly = true)
    public UserTodoSummaryListResponse getAllUserTodos(Long userId, String sortBy) {
        User user = userRepository.findById(userId);

        List<UserTodo> userTodos = userTodoRepository.findAllByUser(user, sortBy);

        List<UserTodoDetailResponse> detailResponses = userTodos.stream()
                .map(todo -> UserTodoDetailResponse.builder()
                        .userTodoId(todo.getId())
                        .task(todo.getTask())
                        .deadLine(todo.getDeadLine())
                        .priority(todo.getPriority().name())
                        .completed(todo.isCompleted())
                        .build())
                .toList();

        return UserTodoSummaryListResponse.builder()
                .userTodoDetailResponses(detailResponses)
                .build();
    }

    @Transactional
    public UserTodoUpdateResponse updateUserTodo(Long userId, Long userTodoId, UserTodoUpdateRequest request) {
        userRepository.findById(userId);

        UserTodo userTodo = userTodoRepository.findByUserTodoId(userTodoId);

        userTodo.updateUserTodo(
                request.task(),
                request.deadLine(),
                Priority.valueOf(request.priority())
        );

        return UserTodoUpdateResponse.builder()
                .userTodoId(userTodo.getId())
                .build();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void deleteExpiredUserTodos() {
        userTodoRepository.deleteByDeadLineBefore(LocalDate.now());
    }

    @Transactional
    public UserTodoDeleteResponse deleteUserTodo(Long userId, Long userTodoId) {
        userRepository.findById(userId);
        UserTodo userTodo = userTodoRepository.findByUserTodoId(userTodoId);

        userTodoRepository.delete(userTodo);

        return UserTodoDeleteResponse.builder()
                .userTodoId(userTodo.getId())
                .build();
    }

}
