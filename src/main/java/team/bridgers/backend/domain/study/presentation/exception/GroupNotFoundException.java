package team.bridgers.backend.domain.study.presentation.exception;

import team.bridgers.backend.global.exception.CustomException;

public class GroupNotFoundException extends CustomException {
    public GroupNotFoundException() {
        super(StudyGroupExceptionCode.GROUP_NOT_FOUND);
    }
}
