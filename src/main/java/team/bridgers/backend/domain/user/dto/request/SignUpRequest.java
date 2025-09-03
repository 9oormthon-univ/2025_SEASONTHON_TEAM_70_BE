package team.bridgers.backend.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import team.bridgers.backend.domain.user.domain.Gender;
import team.bridgers.backend.domain.user.domain.UserType;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record SignUpRequest(

        @NotBlank
        @Schema(description = "(이메일 인증) 이메일 주소", example = "user@example.com", requiredMode = REQUIRED)
        String email,

        @NotBlank
        @Email
        @Schema(description = "로그인 ID (이메일 형식)", example = "user123@example.com", requiredMode = REQUIRED)
        String loginId,

        @NotBlank
        @Schema(description = "닉네임", example = "nickname", requiredMode = REQUIRED)
        String nickname,

        @NotBlank
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$")
        @Schema(description = "비밀번호 (8~15자, 대/소문자, 숫자, 특수문자 포함)", example = "Abcd1234!", requiredMode = REQUIRED)
        String password,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
                message = "<8~15자의 영문 대/소문자, 숫자, 특수문자 포함> 비밀번호 조건을 충족해야 합니다."
        )
        @Schema(description = "비밀번호 확인", example = "Abcd1234!", requiredMode = REQUIRED)
        String confirmPassword,

        @Schema(description = "성별", example = "MALE", requiredMode = REQUIRED)
        Gender gender,

        @NotBlank
        @Pattern(regexp = "^(?=.*\\d).{8}$")
        @Schema(description = "생년월일 (YYYYMMDD 8자리 숫자)", example = "19900101", requiredMode = REQUIRED)
        String birthday,

        @Schema(description = "사용자 유형", example = "STUDENT", requiredMode = REQUIRED)
        UserType type

) {}
