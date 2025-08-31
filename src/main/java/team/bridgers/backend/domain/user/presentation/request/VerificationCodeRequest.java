package team.bridgers.backend.domain.user.presentation.request;

import lombok.Builder;

@Builder
public record VerificationCodeRequest(
        String email,
        String code
) {
}
