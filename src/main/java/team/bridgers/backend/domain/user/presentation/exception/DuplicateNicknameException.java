package team.bridgers.backend.domain.user.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class DuplicateNicknameException extends CustomException {
    public DuplicateNicknameException() {
        super(UserExceptionCode.DUPLICATE_NICKNAME);
    }
}
