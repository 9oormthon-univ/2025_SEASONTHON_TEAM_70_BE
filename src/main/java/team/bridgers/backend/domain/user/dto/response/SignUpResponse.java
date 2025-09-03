package team.bridgers.backend.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "회원가입 응답 객체")
public record SignUpResponse (

        @Schema(description = "회원 고유 아이디", example = "1")
        Long Id

){
}
