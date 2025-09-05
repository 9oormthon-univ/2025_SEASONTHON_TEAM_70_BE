package team.bridgers.backend.domain.board.presentation.exeption;

import team.bridgers.backend.global.exception.CustomException;

public class BoardUnauthorizedAccessExeption extends CustomException {
    public BoardUnauthorizedAccessExeption() {
        super(BoardExceptionCode.UNAUTHORIZED_ACCESS);
    }
}
