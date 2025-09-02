package team.bridgers.backend.domain.user.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class DuplicateLoginIdException extends CustomException {
    public DuplicateLoginIdException() {
        super(UserExceptionCode.DUPLICATE_LOGIN_ID);
    }
}
