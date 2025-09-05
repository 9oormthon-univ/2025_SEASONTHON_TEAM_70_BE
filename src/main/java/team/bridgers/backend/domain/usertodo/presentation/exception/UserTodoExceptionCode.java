package team.bridgers.backend.domain.usertodo.presentation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import team.bridgers.backend.global.exception.ExceptionCode;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum UserTodoExceptionCode implements ExceptionCode {
    USER_TODO_NOT_FOUND(NOT_FOUND, "해당 사용자의 할 일을 찾을 수 없습니다."),
    INVALID_PERIOD(BAD_REQUEST, "지원하지 않는 기간 타입입니다."),
    ;

    private final HttpStatus status;

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
