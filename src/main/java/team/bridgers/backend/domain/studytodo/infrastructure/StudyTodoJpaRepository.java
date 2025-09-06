package team.bridgers.backend.domain.studytodo.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.bridgers.backend.domain.study.domain.UserStudyGroup;
import team.bridgers.backend.domain.studytodo.domain.StudyTodo;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.usertodo.domain.UserTodo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudyTodoJpaRepository extends JpaRepository<StudyTodo, Long> {

    @Query("SELECT s FROM StudyTodo s WHERE s.userStudyGroup = :userStudyGroup ORDER BY " +
            "CASE s.priority " +
            "WHEN team.bridgers.backend.domain.usertodo.domain.Priority.HIGH THEN 3 " +
            "WHEN team.bridgers.backend.domain.usertodo.domain.Priority.MEDIUM THEN 2 " +
            "WHEN team.bridgers.backend.domain.usertodo.domain.Priority.LOW THEN 1 " +
            "END DESC")
    List<StudyTodo> findAllByUserStudyGroupOrderByPriorityDesc(UserStudyGroup userStudyGroup);

    List<StudyTodo> findAllByUserStudyGroupOrderByDeadLineAsc(UserStudyGroup userStudyGroup);

    List<StudyTodo> findAllByUserStudyGroupOrderByCreatedAtDesc(UserStudyGroup userStudyGroup);

    void deleteByDeadLineBeforeAndCompletedFalse(LocalDate deadline);

    @Query("SELECT FUNCTION('DATE', s.completedAt), COUNT(s) " +
            "FROM StudyTodo s " +
            "WHERE s.userStudyGroup.id = :userStudyGroupId AND s.completed = true AND s.completedAt IS NOT NULL " +
            "GROUP BY FUNCTION('DATE', s.completedAt)" +
            "ORDER BY FUNCTION('DATE', s.completedAt)")
    List<Object[]> countCompletedTodosByDay(@Param("userStudyGroupId") Long userStudyGroupId);

    @Query("SELECT FUNCTION('YEARWEEK', s.completedAt), COUNT(s) " +
            "FROM StudyTodo s " +
            "WHERE s.userStudyGroup.id = :userStudyGroupId AND s.completed = true AND s.completedAt IS NOT NULL " +
            "GROUP BY FUNCTION('YEARWEEK', s.completedAt) " +
            "ORDER BY FUNCTION('YEARWEEK', s.completedAt)")
    List<Object[]> countCompletedTodosByWeek(@Param("userStudyGroupId") Long userStudyGroupId);

    @Query("SELECT FUNCTION('DATE_FORMAT', s.completedAt, '%Y-%m'), COUNT(s) " +
            "FROM StudyTodo s " +
            "WHERE s.userStudyGroup.id = :userStudyGroupId AND s.completed = true AND s.completedAt IS NOT NULL " +
            "GROUP BY FUNCTION('DATE_FORMAT', s.completedAt, '%Y-%m') " +
            "ORDER BY FUNCTION('DATE_FORMAT', s.completedAt, '%Y-%m')")
    List<Object[]> countCompletedTodosByMonth(@Param("userStudyGroupId") Long userStudyGroupId);

}
