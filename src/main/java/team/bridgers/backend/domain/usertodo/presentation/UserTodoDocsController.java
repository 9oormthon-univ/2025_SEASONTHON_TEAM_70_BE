package team.bridgers.backend.domain.usertodo.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import team.bridgers.backend.domain.usertodo.dto.request.UserTodoSaveRequest;
import team.bridgers.backend.domain.usertodo.dto.request.UserTodoUpdateRequest;
import team.bridgers.backend.domain.usertodo.dto.response.*;
import team.bridgers.backend.global.annotation.MemberId;

import java.util.List;

@Tag(name = "UserTodo", description = "사용자 할 일(Todo) 관리 API")
@RequestMapping("/user-todos")
public interface UserTodoDocsController {

    @Operation(summary = "할 일 등록", description = "새로운 할 일을 등록합니다.")
    @ApiResponse(responseCode = "201", description = "할 일 등록 성공",
            content = @Content(schema = @Schema(implementation = UserTodoSaveResponse.class)))
    @PostMapping
    ResponseEntity<UserTodoSaveResponse> saveUserTodo(
            @Parameter(hidden = true) @MemberId Long userId,
            @Valid @RequestBody UserTodoSaveRequest request
    );

    @Operation(summary = "할 일 완료 토글", description = "할 일의 완료 여부를 변경합니다.")
    @ApiResponse(responseCode = "200", description = "완료 여부 변경 성공",
            content = @Content(schema = @Schema(implementation = UserTodoUpdateCompletedResponse.class)))
    @PatchMapping("/{userTodoId}/completed")
    ResponseEntity<UserTodoUpdateCompletedResponse> toggleCompleted(
            @Parameter(hidden = true) @MemberId Long userId,
            @PathVariable Long userTodoId
    );

    @Operation(summary = "할 일 목록 조회", description = "사용자의 모든 할 일을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = UserTodoSummaryListResponse.class)))
    @GetMapping
    ResponseEntity<UserTodoSummaryListResponse> getAllUserTodos(
            @Parameter(hidden = true) @MemberId Long userId,
            @RequestParam(defaultValue = "") String sortBy
    );

    @Operation(summary = "할 일 수정", description = "특정 할 일을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "수정 성공",
            content = @Content(schema = @Schema(implementation = UserTodoUpdateResponse.class)))
    @PatchMapping("/{userTodoId}")
    ResponseEntity<UserTodoUpdateResponse> updateUserTodo(
            @Parameter(hidden = true) @MemberId Long userId,
            @PathVariable Long userTodoId,
            @Valid @RequestBody UserTodoUpdateRequest request
    );

    @Operation(summary = "할 일 삭제", description = "특정 할 일을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "삭제 성공",
            content = @Content(schema = @Schema(implementation = UserTodoDeleteResponse.class)))
    @DeleteMapping("/{userTodoId}")
    ResponseEntity<UserTodoDeleteResponse> deleteUserTodo(
            @Parameter(hidden = true) @MemberId Long userId,
            @PathVariable Long userTodoId
    );

    @Operation(summary = "할 일 완료 통계 조회", description = "기간별 할 일 완료 통계를 조회합니다.(day, week, month)")
    @ApiResponse(responseCode = "200", description = "통계 조회 성공",
            content = @Content(schema = @Schema(implementation = CompletedTodoStatsResponse.class)))
    @GetMapping("/stats")
    ResponseEntity<List<CompletedTodoStatsResponse>> getCompletedStats(
            @Parameter(hidden = true) @MemberId Long userId,
            @RequestParam String period
    );

}
