package team.bridgers.backend.domain.studytodo.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team.bridgers.backend.domain.study.domain.UserStudyGroup;
import team.bridgers.backend.domain.studytodo.domain.StudyTodo;
import team.bridgers.backend.domain.studytodo.domain.StudyTodoRepository;
import team.bridgers.backend.domain.studytodo.presentation.exception.StudyTodoNotFoundException;
import team.bridgers.backend.domain.usertodo.infrastructure.UserTodoJpaRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudyTodoRepositoryImpl implements StudyTodoRepository{
    private final StudyTodoJpaRepository studyTodoJpaRepository;

    @Override
    public StudyTodo save(StudyTodo studyTodo) {
        return studyTodoJpaRepository.save(studyTodo);
    }

    @Override
    public StudyTodo findById(Long studyTodoId) {
        return studyTodoJpaRepository.findById(studyTodoId)
                .orElseThrow(StudyTodoNotFoundException::new);
    }

    @Override
    public List<StudyTodo> findAllByUserStudyGroup(UserStudyGroup userStudyGroup, String sortBy) {
        return switch (sortBy) {
            case "priority" -> studyTodoJpaRepository.findAllByUserStudyGroupOrderByPriorityDesc(userStudyGroup);
            case "deadline" -> studyTodoJpaRepository.findAllByUserStudyGroupOrderByDeadLineAsc(userStudyGroup);
            default -> studyTodoJpaRepository.findAllByUserStudyGroupOrderByCreatedAtDesc(userStudyGroup);
        };
    }

    @Override
    public void deleteByDeadLineBeforeAndCompletedFalse(LocalDate deadline) {
        studyTodoJpaRepository.deleteByDeadLineBeforeAndCompletedFalse(deadline);
    }

    @Override
    public void delete(StudyTodo studyTodo) {
        studyTodoJpaRepository.delete(studyTodo);
    }

    @Override
    public List<Object[]> countCompletedTodosByDay(@Param("userStudyGroupId") Long userStudyGroupId) {
        return studyTodoJpaRepository.countCompletedTodosByDay(userStudyGroupId);
    }

    @Override
    public List<Object[]> countCompletedTodosByWeek(@Param("userStudyGroupId") Long userStudyGroupId) {
        return studyTodoJpaRepository.countCompletedTodosByWeek(userStudyGroupId);
    }

    @Override
    public List<Object[]> countCompletedTodosByMonth(@Param("userStudyGroupId") Long userStudyGroupId) {
        return studyTodoJpaRepository.countCompletedTodosByMonth(userStudyGroupId);
    }
}
