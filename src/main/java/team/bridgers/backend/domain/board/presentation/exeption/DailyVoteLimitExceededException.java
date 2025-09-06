package team.bridgers.backend.domain.board.presentation.exeption;

import team.bridgers.backend.global.exception.CustomException;

public class DailyVoteLimitExceededException extends CustomException {
    public DailyVoteLimitExceededException() {
        super(VoteExceptionCode.DAILY_VOTE_LIMIT_EXCEEDED);
    }
}
