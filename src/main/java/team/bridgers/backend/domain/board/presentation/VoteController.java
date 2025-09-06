package team.bridgers.backend.domain.board.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.bridgers.backend.domain.board.application.VoteService;
import team.bridgers.backend.domain.board.presentation.response.VoteResponse;
import team.bridgers.backend.global.annotation.MemberId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/votes")
public class VoteController {
    private final VoteService voteService;

    @PostMapping("/board/{boardId}")
    public ResponseEntity<VoteResponse> voteForBoard(@MemberId Long memberId, @PathVariable Long boardId) {
        VoteResponse response = voteService.voteForBoard(memberId, boardId);
        return ResponseEntity.ok(response);
    }

}
