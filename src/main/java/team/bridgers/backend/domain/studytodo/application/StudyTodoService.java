package team.bridgers.backend.domain.studytodo.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.bridgers.backend.domain.study.domain.UserStudyGroup;
import team.bridgers.backend.domain.study.domain.UserStudyGroupRepository;
import team.bridgers.backend.domain.studytodo.domain.StudyTodo;
import team.bridgers.backend.domain.studytodo.domain.StudyTodoRepository;
import team.bridgers.backend.domain.studytodo.dto.request.CreateStudyTodoRequest;
import team.bridgers.backend.domain.studytodo.dto.request.UpdateStudyTodoCompleteRequest;
import team.bridgers.backend.domain.studytodo.dto.request.UpdateStudyTodoRequest;
import team.bridgers.backend.domain.studytodo.dto.response.*;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.UserRepository;
import team.bridgers.backend.domain.usertodo.domain.Priority;
import team.bridgers.backend.domain.usertodo.dto.response.CompletedTodoStatsResponse;
import team.bridgers.backend.domain.usertodo.dto.response.UserTodoDetailResponse;
import team.bridgers.backend.domain.usertodo.presentation.exception.InvalidPeriodException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyTodoService {
    private final StudyTodoRepository studyTodoRepository;
    private final UserStudyGroupRepository userStudyGroupRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateStudyTodoResponse saveStudyTodo(Long userId, CreateStudyTodoRequest request) {
        userRepository.findById(userId);

        UserStudyGroup userStudyGroup = userStudyGroupRepository.findById(request.userStudyGroupId());

        StudyTodo studyTodo = StudyTodo.builder()
                .userStudyGroup(userStudyGroup)
                .task(request.task())
                .deadLine(request.deadLine())
                .priority(Priority.valueOf(request.priority()))
                .build();

        studyTodoRepository.save(studyTodo);

        return CreateStudyTodoResponse.builder()
                .studyTodoId(studyTodo.getId())
                .build();
    }

    @Transactional
    public UpdateStudyTodoCompleteResponse toggleCompleted(
            UpdateStudyTodoCompleteRequest request,
            Long studyTodoId
    ) {

        userStudyGroupRepository.findById(request.userStudyGroupId());

        StudyTodo studyTodo = studyTodoRepository.findById(studyTodoId);

        if (studyTodo.isCompleted()) {
            studyTodo.uncomplete();
        } else {
            studyTodo.complete();
        }

        return UpdateStudyTodoCompleteResponse.builder()
                .studyTodoId(studyTodo.getId())
                .complete(studyTodo.isCompleted())
                .build();
    }

    @Transactional(readOnly = true)
    public StudyTodoSummaryListResponse getAllStudyTodos(Long userStudyGroupId, String sortBy) {
        UserStudyGroup userStudyGroup = userStudyGroupRepository.findById(userStudyGroupId);

        List<StudyTodo> studyTodos = studyTodoRepository.findAllByUserStudyGroup(userStudyGroup, sortBy);

        List<StudyTodoDetailResponse> detailResponses = studyTodos.stream()
                .map(studyTodo -> StudyTodoDetailResponse.builder()
                        .studyTodoId(studyTodo.getId())
                        .task(studyTodo.getTask())
                        .deadLine(studyTodo.getDeadLine())
                        .priority(studyTodo.getPriority().name())
                        .completed(studyTodo.isCompleted())
                        .build())
                .toList();

        return StudyTodoSummaryListResponse.builder()
                .studyTodoDetailResponses(detailResponses)
                .build();
    }

    @Transactional
    public UpdateStudyTodoResponse updateStudyTodo(Long studyTodoId, UpdateStudyTodoRequest request) {
        userStudyGroupRepository.findById(request.userStudyGroupId());

        StudyTodo studyTodo = studyTodoRepository.findById(studyTodoId);

        studyTodo.updateGroupTodo(
                request.task(),
                request.deadLine(),
                Priority.valueOf(request.priority())
        );

        return UpdateStudyTodoResponse.builder()
                .studyTodoId(studyTodo.getId())
                .build();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void deleteExpiredStudyTodos() {
        studyTodoRepository.deleteByDeadLineBeforeAndCompletedFalse(LocalDate.now());
    }

    @Transactional
    public DeleteStudyTodoResponse deleteStudyTodo(Long userStudyGroupId, Long studyTodoId) {
        userStudyGroupRepository.findById(userStudyGroupId);
        StudyTodo studyTodo = studyTodoRepository.findById(studyTodoId);

        studyTodoRepository.delete(studyTodo);

        return DeleteStudyTodoResponse.builder()
                .studyTodoId(studyTodo.getId())
                .build();
    }

    @Transactional(readOnly = true)
    public List<CompletedTodoStatsResponse> getCompletedStats(Long userStudyGroupId, String period) {
        List<Object[]> results;

        switch (period) {
            case "day" -> results = studyTodoRepository.countCompletedTodosByDay(userStudyGroupId);
            case "week" -> results = studyTodoRepository.countCompletedTodosByWeek(userStudyGroupId);
            case "month" -> results = studyTodoRepository.countCompletedTodosByMonth(userStudyGroupId);
            default -> throw new InvalidPeriodException();
        }

        return results.stream()
                .map(r -> CompletedTodoStatsResponse.builder()
                        .period(r[0].toString())
                        .completedCount(((Number) r[1]).intValue())
                        .build()
                )
                .toList();
    }
}
