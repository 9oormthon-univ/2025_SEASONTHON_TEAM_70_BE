package team.bridgers.backend.domain.email.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class VerificationCodeNotFoundException extends CustomException {
    public VerificationCodeNotFoundException() {
        super(VerificationCodeExceptionCode.VERIFICATION_CODE_NOT_FOUND);
    }
}
