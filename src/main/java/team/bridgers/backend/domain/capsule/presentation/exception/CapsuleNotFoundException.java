package team.bridgers.backend.domain.capsule.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class CapsuleNotFoundException extends CustomException {
    public CapsuleNotFoundException() {
        super(CapsuleExceptionCode.CAPSULE_NOT_FOUND);
    }
}
