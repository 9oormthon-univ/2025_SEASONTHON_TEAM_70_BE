package team.bridgers.backend.domain.usertodo.domain;

import org.springframework.data.repository.query.Param;
import team.bridgers.backend.domain.user.domain.User;

import java.time.LocalDate;
import java.util.List;

public interface UserTodoRepository {

    void save(UserTodo userTodo);

    UserTodo findByUserTodoId(Long userTodoId);

    List<UserTodo> findAllByUser(User user, String sortBy);

    void deleteByDeadLineBeforeAndCompletedFalse(LocalDate deadline);

    void delete(UserTodo userTodo);

    List<Object[]> countCompletedTodosByDay(@Param("userId") Long userId);

    List<Object[]> countCompletedTodosByWeek(@Param("userId") Long userId);

    List<Object[]> countCompletedTodosByMonth(@Param("userId") Long userId);

}
