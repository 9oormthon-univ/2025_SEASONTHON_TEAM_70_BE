package team.bridgers.backend.domain.usertodo.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.usertodo.domain.UserTodo;
import team.bridgers.backend.domain.usertodo.domain.UserTodoRepository;
import team.bridgers.backend.domain.usertodo.presentation.exception.UserTodoNotFoundException;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserTodoRepositoryImpl implements UserTodoRepository {

    private final UserTodoJpaRepository userTodoJpaRepository;

    @Override
    public void save(UserTodo userTodo) {
        userTodoJpaRepository.save(userTodo);
    }

    @Override
    public UserTodo findByUserTodoId(Long userTodoId) {
        return userTodoJpaRepository.findById(userTodoId)
                .orElseThrow(UserTodoNotFoundException::new);
    }

    @Override
    public List<UserTodo> findAllByUser(User user, String sortBy) {
        return switch (sortBy) {
            case "priority" -> userTodoJpaRepository.findAllByUserOrderByPriorityDesc(user);
            case "deadline" -> userTodoJpaRepository.findAllByUserOrderByDeadLineAsc(user);
            default -> userTodoJpaRepository.findAllByUserOrderByCreatedAtDesc(user);
        };
    }

    @Override
    public void deleteByDeadLineBefore(LocalDate deadline) {
        userTodoJpaRepository.deleteByDeadLineBefore(deadline);
    }

    @Override
    public void delete(UserTodo userTodo) {
        userTodoJpaRepository.delete(userTodo);
    }

}
