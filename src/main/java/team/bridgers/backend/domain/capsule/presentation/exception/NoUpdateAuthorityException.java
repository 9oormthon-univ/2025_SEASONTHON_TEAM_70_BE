package team.bridgers.backend.domain.capsule.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class NoUpdateAuthorityException extends CustomException {
    public NoUpdateAuthorityException() {
        super(CapsuleExceptionCode.NO_UPDATE_AUTHORITY);
    }
}
