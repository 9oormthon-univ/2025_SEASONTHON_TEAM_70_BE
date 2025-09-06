package team.bridgers.backend.domain.board.presentation.exeption;

import team.bridgers.backend.global.exception.CustomException;

public class VoteNotFoundException extends CustomException {
    public VoteNotFoundException() {
        super(VoteExceptionCode.VOTE_NOT_FOUND);
    }
}
