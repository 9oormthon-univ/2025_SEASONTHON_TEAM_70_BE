package team.bridgers.backend.domain.user.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class PasswordMismatchException extends CustomException {
    public PasswordMismatchException() {
        super(UserExceptionCode.PASSWORD_MISMATCH);
    }
}
