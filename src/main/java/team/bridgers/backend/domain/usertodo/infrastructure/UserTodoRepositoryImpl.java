package team.bridgers.backend.domain.usertodo.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.bridgers.backend.domain.usertodo.domain.UserTodo;
import team.bridgers.backend.domain.usertodo.domain.UserTodoRepository;
import team.bridgers.backend.domain.usertodo.presentation.exception.UserTodoNotFoundException;

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

}
