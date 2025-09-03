package team.bridgers.backend.global.auth.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoginResultRequest(

        @NotNull(message = "로그인 아이디를 입력해 주세요.")
        String loginId,

        @NotNull(message = "비밀번호를 입력해 주세요.")
        String password

) {
}
