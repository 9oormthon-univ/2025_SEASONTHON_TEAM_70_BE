package team.bridgers.backend.domain.usertodo.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.usertodo.domain.UserTodo;

public interface UserTodoJpaRepository extends JpaRepository<UserTodo, Long> {
    UserTodo user(User user);
}
