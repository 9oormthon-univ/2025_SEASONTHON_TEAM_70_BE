package team.bridgers.backend.global.auth.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class RefreshTokenNotValidException extends CustomException {
    public RefreshTokenNotValidException() {
        super(AuthExceptionCode.AUTHENTICATION_REQUIRED);
    }
}
