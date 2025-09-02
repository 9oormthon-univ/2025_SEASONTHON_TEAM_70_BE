package team.bridgers.backend.domain.user.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super(UserExceptionCode.USER_NOT_FOUND);
    }
}
