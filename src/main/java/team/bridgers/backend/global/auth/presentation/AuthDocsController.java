package team.bridgers.backend.global.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import team.bridgers.backend.global.auth.dto.request.LoginResultRequest;
import team.bridgers.backend.global.auth.dto.response.LoginResultResponse;

@Tag(name = "Login", description = "로그인 API")
public interface AuthDocsController {

    @Operation(summary = "로그인", description = "사용자 로그인 처리")
    @ApiResponse(responseCode = "200", description = "로그인 성공",
            content = @Content(schema = @Schema(implementation = LoginResultResponse.class)))
    @ApiResponse(responseCode = "400", description = "잘못된 요청 (유효성 검사 실패)")
    @PostMapping
    ResponseEntity<LoginResultResponse> login(
            @Valid @RequestBody LoginResultRequest request,
            HttpServletResponse response
    );

}
