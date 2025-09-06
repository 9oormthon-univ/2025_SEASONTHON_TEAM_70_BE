package team.bridgers.backend.domain.board.presentation.exeption;

import team.bridgers.backend.global.exception.CustomException;

public class ContentLengthExceededException extends CustomException {
    public ContentLengthExceededException() {
        super(CommentExceptionCode.CONTENT_LENGTH_EXCEEDED);
    }
}
