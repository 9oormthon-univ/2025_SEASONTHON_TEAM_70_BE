package team.bridgers.backend.domain.board.presentation.exeption;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import team.bridgers.backend.global.exception.ExceptionCode;

@RequiredArgsConstructor
public enum CommentExceptionCode implements ExceptionCode {
    CONTENT_LENGTH_EXCEEDED(400, "댓글 길이를 초과했습니다."),
    UNAUTHORIZED_ACCESS(403, "권한이 없습니다."),
    COMMENT_NOT_FOUND(404, "댓글을 찾을 수 없습니다.");

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
