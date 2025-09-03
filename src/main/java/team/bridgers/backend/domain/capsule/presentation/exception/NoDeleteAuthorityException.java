package team.bridgers.backend.domain.capsule.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class NoDeleteAuthorityException extends CustomException {
    public NoDeleteAuthorityException() {
        super(CapsuleExceptionCode.NO_DELETE_AUTHORITY);
    }
}
