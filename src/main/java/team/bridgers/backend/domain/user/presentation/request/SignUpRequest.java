package team.bridgers.backend.domain.user.presentation.request;

import lombok.Builder;

@Builder
public record SignUpRequest (
        String email,
        String userId,
        String nickname,
        String password,
        String gender,
        String birthday,
        String type
){

}
