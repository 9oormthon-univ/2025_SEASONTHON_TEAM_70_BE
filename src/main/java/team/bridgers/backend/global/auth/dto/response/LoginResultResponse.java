package team.bridgers.backend.global.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 성공 시 반환되는 토큰 정보")
public record LoginResultResponse(

        @Schema(description = "접근 토큰(access token)", example = "eyJhbGciOiJIUzI1NiIsIn...")
        String accessToken,

        @Schema(description = "갱신 토큰(refresh token)", example = "dGhpcyBpcyBhIHJlZnJlc2g...")
        String refreshToken

) {}
