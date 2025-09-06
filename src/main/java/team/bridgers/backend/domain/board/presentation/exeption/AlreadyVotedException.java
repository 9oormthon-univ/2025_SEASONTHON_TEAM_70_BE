package team.bridgers.backend.domain.board.presentation.exeption;

import team.bridgers.backend.global.exception.CustomException;

public class AlreadyVotedException extends CustomException {
    public AlreadyVotedException() {
        super(VoteExceptionCode.ALREADY_VOTED);
    }
}

