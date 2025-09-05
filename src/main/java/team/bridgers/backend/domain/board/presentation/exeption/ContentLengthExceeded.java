package team.bridgers.backend.domain.board.presentation.exeption;

import team.bridgers.backend.global.exception.CustomException;

public class ContentLengthExceeded extends CustomException {
    public ContentLengthExceeded() {
        super(CommentExceptionCode.CONTENT_LENGTH_EXCEEDED);
    }
}
