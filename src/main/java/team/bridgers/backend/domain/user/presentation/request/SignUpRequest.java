package team.bridgers.backend.domain.user.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.lang.Nullable;
import team.bridgers.backend.domain.user.domain.Gender;
import team.bridgers.backend.domain.user.domain.Interest;
import team.bridgers.backend.domain.user.domain.UserType;

import java.util.List;

@NotBlank
@Builder
public record SignUpRequest (

        @NotBlank
        String email,

        @NotBlank
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d){1,12}$")
        String userId,

        @NotBlank
        @Pattern(regexp = "^")
        String nickname,

        @NotBlank
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$")
        String password,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
                message = "<8~15자의 영문 대/소문자, 숫자, 특수문자 포함> 비밀번호 조건을 충족해야 합니다."
        )
        String confirmPassword,

        @NotBlank
        Gender gender,

        @NotBlank
        @Pattern(regexp = "^(?=.*\\d).{8}$")
        String birthday,

        @NotBlank
        UserType type,

        @Nullable
        List<Interest> interest
){

}
