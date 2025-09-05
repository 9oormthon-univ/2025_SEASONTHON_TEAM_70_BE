package team.bridgers.backend.domain.board.presentation.exeption;

import team.bridgers.backend.global.exception.CustomException;

public class BoardNotFoundExeption extends CustomException {
    public BoardNotFoundExeption() {
        super(BoardExceptionCode.BOARD_NOT_FOUND);
    }
}
