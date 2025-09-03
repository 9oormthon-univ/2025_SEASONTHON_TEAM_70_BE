package team.bridgers.backend.domain.capsule.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class SameVisibilityException extends CustomException {
    public SameVisibilityException() {
        super(CapsuleExceptionCode.SAME_VISIBILITY);
    }
}
