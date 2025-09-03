package team.bridgers.backend.global.auth.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class AuthenticationRequiredException extends CustomException {
    public AuthenticationRequiredException() {
        super(AuthExceptionCode.AUTHENTICATION_REQUIRED);
    }
}
