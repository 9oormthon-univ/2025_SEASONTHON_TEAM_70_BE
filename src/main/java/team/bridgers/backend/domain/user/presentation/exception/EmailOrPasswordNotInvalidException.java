package team.bridgers.backend.domain.user.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class EmailOrPasswordNotInvalidException extends CustomException {
    public EmailOrPasswordNotInvalidException() {
        super(UserExceptionCode.EMAIL_OR_PASSWORD_NOT_INVALID);
    }
}
