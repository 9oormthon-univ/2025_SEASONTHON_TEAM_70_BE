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
import team.bridgers.backend.domain.board.presentation.response.BoardResponse;
import team.bridgers.backend.global.annotation.MemberId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@MemberId Long userId, @Valid @RequestBody BoardRequest boardRequest) {
        BoardResponse response=boardService.createBoard(boardRequest.boardType(), userId, boardRequest.boardTitle(), boardRequest.boardContent());
        return ResponseEntity.ok(response);
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

    @GetMapping("/search")
    public ResponseEntity<Page<BoardPageResponse>> searchBoards(@RequestParam String keyword,
                                                               @RequestParam(required = false) BoardType boardType,
                                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size,
                                                               @RequestParam(value = "sortBy", defaultValue = "NEWEST") String sortBy) {
        Page<BoardPageResponse> boards = boardService.searchBoards(keyword, boardType, page, size, sortBy);
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDetailResponse> getBoard(@PathVariable Long id) {
        BoardDetailResponse boardDetail = boardService.getBoard(id);
        return ResponseEntity.ok(boardDetail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BoardResponse> deleteBoard(@PathVariable Long id, @MemberId Long userId) {
        BoardResponse response = boardService.deleteBoard(id, userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable Long id, @MemberId Long userId, @Valid @RequestBody BoardRequest boardRequest) {
        BoardResponse response = boardService.updateBoard(id, userId, boardRequest.boardTitle(), boardRequest.boardContent());
        return ResponseEntity.ok(response);
    }
}
