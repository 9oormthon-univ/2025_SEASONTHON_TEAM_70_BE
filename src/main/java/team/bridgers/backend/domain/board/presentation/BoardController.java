package team.bridgers.backend.domain.board.presentation;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.bridgers.backend.domain.board.application.BoardService;
import team.bridgers.backend.domain.board.domain.BoardType;
import team.bridgers.backend.domain.board.presentation.request.BoardRequest;
import team.bridgers.backend.domain.board.presentation.response.BoardDetailResponse;
import team.bridgers.backend.domain.board.presentation.response.BoardPageResponse;
import team.bridgers.backend.global.annotation.MemberId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<String> createBoard(@MemberId Long userId, @Valid BoardRequest boardRequest) {
        boardService.createBoard(boardRequest.getBoardType(), userId, boardRequest.getBoardTitle(), boardRequest.getBoardContent());
        return ResponseEntity.ok("게시판이 성공적으로 생성되었습니다.");
    }

    @GetMapping("/list")
    public ResponseEntity<Page<BoardPageResponse>> getBoards(@RequestParam BoardType boardType,
                                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                                             @RequestParam(value = "sortBy", defaultValue = "NEWEST") String sortBy)
    {
        Page<BoardPageResponse> boards = boardService.getBoards(boardType,page,size,sortBy);
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDetailResponse> getBoard(@PathVariable Long id) {
        BoardDetailResponse boardDetail = boardService.getBoard(id);
        return ResponseEntity.ok(boardDetail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id, @MemberId Long userId) {
        boardService.deleteBoard(id, userId);
        return ResponseEntity.ok("게시판이 성공적으로 삭제되었습니다.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBoard(@PathVariable Long id, @MemberId Long userId, @Valid BoardRequest boardRequest) {
        boardService.updateBoard(id, userId, boardRequest.getBoardTitle(), boardRequest.getBoardContent());
        return ResponseEntity.ok("게시판이 성공적으로 수정되었습니다.");
    }
}
