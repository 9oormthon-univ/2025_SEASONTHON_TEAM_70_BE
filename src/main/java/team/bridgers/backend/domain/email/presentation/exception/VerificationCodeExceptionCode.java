package team.bridgers.backend.domain.email.presentation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import team.bridgers.backend.global.exception.ExceptionCode;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Getter
@RequiredArgsConstructor
public enum VerificationCodeExceptionCode implements ExceptionCode {
    VERIFICATION_CODE_NOT_FOUND(NOT_FOUND,"인증코드가 존재하지 않습니다."),
    ;

    private final HttpStatus status;

    private final String message;


    @Override
    public String getCode() { return this.name(); }
}
