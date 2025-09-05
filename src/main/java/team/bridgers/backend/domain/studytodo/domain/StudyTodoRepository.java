package team.bridgers.backend.domain.studytodo.domain;

import org.springframework.data.repository.query.Param;
import team.bridgers.backend.domain.study.domain.UserStudyGroup;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.usertodo.domain.UserTodo;

import java.time.LocalDate;
import java.util.List;

public interface StudyTodoRepository {
    StudyTodo save(StudyTodo studyTodo);

    StudyTodo findById(Long studyTodoId);

    List<StudyTodo> findAllByUserStudyGroup(UserStudyGroup userStudyGroup, String sortBy);

    void deleteByDeadLineBeforeAndCompletedFalse(LocalDate deadline);

    void delete(StudyTodo studyTodo);

    List<Object[]> countCompletedTodosByDay(@Param("userStudyId") Long userStudyGroupId);

    List<Object[]> countCompletedTodosByWeek(@Param("userStudyId") Long userStudyGroupId);

    List<Object[]> countCompletedTodosByMonth(@Param("userStudyId") Long userStudyGroupId);
}
