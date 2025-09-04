package team.bridgers.backend.domain.study.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class DuplicateGroupNameException extends CustomException {
    public DuplicateGroupNameException() { super(StudyGroupExceptionCode.DUPLICATE_GROUP_NAME); }
}
