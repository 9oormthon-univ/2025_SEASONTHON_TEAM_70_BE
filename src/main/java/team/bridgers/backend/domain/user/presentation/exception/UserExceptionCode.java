package team.bridgers.backend.domain.user.presentation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import team.bridgers.backend.global.exception.ExceptionCode;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum UserExceptionCode implements ExceptionCode {
    USER_NOT_FOUND(NOT_FOUND, "가입된 사용자가 없습니다."),
    EMAIL_OR_PASSWORD_NOT_INVALID(BAD_REQUEST, "이메일 또는 비밀번호가 올바르지 않습니다."),
    ;

    private final HttpStatus status;

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
