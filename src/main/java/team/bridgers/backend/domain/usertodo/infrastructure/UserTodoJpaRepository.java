package team.bridgers.backend.domain.usertodo.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.usertodo.domain.UserTodo;

import java.util.List;

public interface UserTodoJpaRepository extends JpaRepository<UserTodo, Long> {

    List<UserTodo> findAllByUserOrderByCreatedAtDesc(User user);

    @Query("SELECT u FROM UserTodo u WHERE u.user = :user ORDER BY " +
            "CASE u.priority " +
            "WHEN team.bridgers.backend.domain.usertodo.domain.Priority.HIGH THEN 3 " +
            "WHEN team.bridgers.backend.domain.usertodo.domain.Priority.MEDIUM THEN 2 " +
            "WHEN team.bridgers.backend.domain.usertodo.domain.Priority.LOW THEN 1 " +
            "END DESC")
    List<UserTodo> findAllByUserOrderByPriorityDesc(User user);

    List<UserTodo> findAllByUserOrderByDeadLineAsc(User user);

}
