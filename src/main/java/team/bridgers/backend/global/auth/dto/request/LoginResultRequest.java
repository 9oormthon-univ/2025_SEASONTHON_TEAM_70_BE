package team.bridgers.backend.global.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record LoginResultRequest(

        @NotNull(message = "로그인 아이디를 입력해 주세요.")
        @Schema(description = "로그인 아이디", example = "testuser@example.com", requiredMode = REQUIRED)
        String loginId,

        @NotNull(message = "비밀번호를 입력해 주세요.")
        @Schema(description = "비밀번호", example = "P@ssw0rd!", requiredMode = REQUIRED)
        String password

) {}
