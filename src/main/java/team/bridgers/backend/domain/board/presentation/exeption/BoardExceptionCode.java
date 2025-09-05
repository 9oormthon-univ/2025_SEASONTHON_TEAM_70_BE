package team.bridgers.backend.domain.board.presentation.exeption;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import team.bridgers.backend.global.exception.ExceptionCode;

@RequiredArgsConstructor
public enum BoardExceptionCode implements ExceptionCode {
    UNAUTHORIZED_ACCESS(403, "권한이 없습니다."),
    BOARD_NOT_FOUND(404, "게시물을 찾을 수 없습니다.");

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