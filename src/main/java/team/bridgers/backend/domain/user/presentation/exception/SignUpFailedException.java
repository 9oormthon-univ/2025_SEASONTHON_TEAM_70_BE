package team.bridgers.backend.domain.user.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class SignUpFailedException extends CustomException {
    public SignUpFailedException() {
        super(UserExceptionCode.SIGNUP_FAILED);
    }
}
