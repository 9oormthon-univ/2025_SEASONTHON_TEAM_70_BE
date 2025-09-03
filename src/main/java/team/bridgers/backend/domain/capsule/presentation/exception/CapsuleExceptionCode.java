package team.bridgers.backend.domain.capsule.presentation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import team.bridgers.backend.global.exception.ExceptionCode;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Getter
@RequiredArgsConstructor
public enum CapsuleExceptionCode implements ExceptionCode {
    CAPSULE_NOT_FOUND(NOT_FOUND, "존재하지 않는 캡슐입니다."),
    SAME_VISIBILITY(BAD_REQUEST, "캡슐의 상태가 요청하신 상태와 같습니다."),
    NO_DELETE_AUTHORITY(FORBIDDEN, "삭제 권한이 없는 회원입니다."),
    NO_UPDATE_AUTHORITY(FORBIDDEN, "수정 권한이 없는 회원입니다."),
    ;

    private final HttpStatus status;

    private final String message;


    @Override
    public String getCode() { return this.name(); }
}
