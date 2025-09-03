package team.bridgers.backend.domain.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import team.bridgers.backend.domain.user.dto.request.SignUpRequest;
import team.bridgers.backend.domain.user.dto.response.SignUpResponse;

@Tag(name = "User", description = "회원 가입 API")
public interface UserDocsController {

    @Operation(summary = "회원 가입", description = "신규 사용자를 회원으로 등록합니다.")
    @ApiResponse(responseCode = "200", description = "회원 가입 성공",
            content = @Content(schema = @Schema(implementation = SignUpResponse.class)))
    @ApiResponse(responseCode = "400", description = "잘못된 요청 (유효성 검사 실패)")
    @PostMapping("/sign-up")
    ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request);

}
