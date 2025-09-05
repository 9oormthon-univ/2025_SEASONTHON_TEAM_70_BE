package team.bridgers.backend.domain.board.presentation.exeption;

import team.bridgers.backend.global.exception.CustomException;

public class CommentNotFoundException extends CustomException {
    public CommentNotFoundException() {
        super(CommentExceptionCode.COMMENT_NOT_FOUND);
    }
}
