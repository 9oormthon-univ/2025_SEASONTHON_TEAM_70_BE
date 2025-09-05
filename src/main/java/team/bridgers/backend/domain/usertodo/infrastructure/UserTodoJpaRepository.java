package team.bridgers.backend.domain.usertodo.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.usertodo.domain.UserTodo;

import java.time.LocalDate;
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

    void deleteByDeadLineBeforeAndCompletedFalse(LocalDate deadline);

    @Query("SELECT FUNCTION('DATE', u.completedAt), COUNT(u) " +
            "FROM UserTodo u " +
            "WHERE u.user.id = :userId AND u.completed = true AND u.completedAt IS NOT NULL " +
            "GROUP BY FUNCTION('DATE', u.completedAt) " +
            "ORDER BY FUNCTION('DATE', u.completedAt)")
    List<Object[]> countCompletedTodosByDay(@Param("userId") Long userId);

    @Query("SELECT FUNCTION('YEARWEEK', u.completedAt), COUNT(u) " +
            "FROM UserTodo u " +
            "WHERE u.user.id = :userId AND u.completed = true AND u.completedAt IS NOT NULL " +
            "GROUP BY FUNCTION('YEARWEEK', u.completedAt) " +
            "ORDER BY FUNCTION('YEARWEEK', u.completedAt)")
    List<Object[]> countCompletedTodosByWeek(@Param("userId") Long userId);

    @Query("SELECT FUNCTION('DATE_FORMAT', u.completedAt, '%Y-%m'), COUNT(u) " +
            "FROM UserTodo u " +
            "WHERE u.user.id = :userId AND u.completed = true AND u.completedAt IS NOT NULL " +
            "GROUP BY FUNCTION('DATE_FORMAT', u.completedAt, '%Y-%m') " +
            "ORDER BY FUNCTION('DATE_FORMAT', u.completedAt, '%Y-%m')")
    List<Object[]> countCompletedTodosByMonth(@Param("userId") Long userId);

}
