package team.bridgers.backend.domain.user.dto.response;

import jakarta.persistence.Column;
import lombok.Builder;
import team.bridgers.backend.domain.user.domain.Gender;
import team.bridgers.backend.domain.user.domain.UserType;

@Builder
public record UserInfoResponse(
        String email,
        String nickname,
        Gender gender,
        String birthday,
        UserType type
) {
}
