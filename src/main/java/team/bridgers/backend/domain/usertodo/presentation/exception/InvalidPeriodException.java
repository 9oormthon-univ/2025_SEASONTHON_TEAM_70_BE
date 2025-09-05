package team.bridgers.backend.domain.usertodo.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class InvalidPeriodException extends CustomException {
    public InvalidPeriodException() {
        super(UserTodoExceptionCode.INVALID_PERIOD);
    }
}
