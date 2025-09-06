package team.bridgers.backend.domain.studytodo.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class StudyTodoNotFoundException extends CustomException {
    public StudyTodoNotFoundException() {
        super(StudyTodoExceptionCode.STUDY_TODO_NOT_FOUND);
    }
}
