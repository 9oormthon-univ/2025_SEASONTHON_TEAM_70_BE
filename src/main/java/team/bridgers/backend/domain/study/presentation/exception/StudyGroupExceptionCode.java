package team.bridgers.backend.domain.study.presentation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import team.bridgers.backend.global.exception.ExceptionCode;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum StudyGroupExceptionCode implements ExceptionCode {
    DUPLICATE_GROUP_NAME(BAD_REQUEST, "중복되는 스터디 이름입니다."),
    GROUP_NOT_FOUND(NOT_FOUND, "생성된 스터디가 없습니다."),
    ;

    private final HttpStatus status;

    private final String message;

    @Override
    public String getCode() {return this.name();}
}
