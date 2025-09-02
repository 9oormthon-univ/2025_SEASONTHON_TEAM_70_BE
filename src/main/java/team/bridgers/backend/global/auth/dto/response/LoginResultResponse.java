package team.bridgers.backend.global.auth.dto.response;

public record LoginResultResponse(

        String accessToken,

        String refreshToken

) {
}
