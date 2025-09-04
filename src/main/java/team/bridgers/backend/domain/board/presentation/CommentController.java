package team.bridgers.backend.domain.board.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.bridgers.backend.domain.board.application.CommentService;
import team.bridgers.backend.domain.board.presentation.request.CommentRequest;
import team.bridgers.backend.domain.board.presentation.response.CommentResponse;
import team.bridgers.backend.domain.board.presentation.response.CommentDetailResponse;
import team.bridgers.backend.global.annotation.MemberId;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@MemberId Long memberId,
                                                         @RequestBody @Valid CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.createComment(memberId, commentRequest.BoardId(), commentRequest.commentContent());
        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<CommentDetailResponse>> getCommentsByBoard(@PathVariable Long boardId) {
        List<CommentDetailResponse> comments = commentService.getCommentsByBoard(boardId);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id,
                                                        @MemberId Long memberId,
                                                        @RequestBody @Valid CommentRequest commentRequest) {
        CommentResponse response = commentService.updateComment(id, memberId, commentRequest.commentContent());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommentResponse> deleteComment(@PathVariable Long id, @MemberId Long memberId) {
        CommentResponse response = commentService.deleteComment(id, memberId);
        return ResponseEntity.ok(response);
    }
}
