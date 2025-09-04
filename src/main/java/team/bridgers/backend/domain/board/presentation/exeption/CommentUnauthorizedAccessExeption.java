package team.bridgers.backend.domain.board.presentation.exeption;

import team.bridgers.backend.global.exception.CustomException;

public class CommentUnauthorizedAccessExeption extends CustomException {
    public CommentUnauthorizedAccessExeption() {
        super(CommentExceptionCode.UNAUTHORIZED_ACCESS);
    }
}
