package team.bridgers.backend.domain.usertodo.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.bridgers.backend.domain.usertodo.domain.UserTodo;
import team.bridgers.backend.domain.usertodo.domain.UserTodoRepository;

@RequiredArgsConstructor
@Repository
public class UserTodoRepositoryImpl implements UserTodoRepository {

    private final UserTodoJpaRepository userTodoJpaRepository;

    @Override
    public void save(UserTodo userTodo) {
        userTodoJpaRepository.save(userTodo);
    }

}
