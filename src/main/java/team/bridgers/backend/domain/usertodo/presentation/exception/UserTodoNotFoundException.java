package team.bridgers.backend.domain.usertodo.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class UserTodoNotFoundException extends CustomException {
    public UserTodoNotFoundException() {
        super(UserTodoExceptionCode.USER_TODO_NOT_FOUND);
    }
}
