package team.bridgers.backend.domain.board.presentation.exeption;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import team.bridgers.backend.global.exception.ExceptionCode;

@RequiredArgsConstructor
public enum VoteExceptionCode implements ExceptionCode {
    ALREADY_VOTED(400, "이미 투표한 게시물입니다."),
    DAILY_VOTE_LIMIT_EXCEEDED(400, "하루 투표 횟수를 초과했습니다."),
    VOTE_NOT_FOUND(404, "투표를 찾을 수 없습니다.");

    private final int status;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.valueOf(this.status);
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
