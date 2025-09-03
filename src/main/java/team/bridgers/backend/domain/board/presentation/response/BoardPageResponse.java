package team.bridgers.backend.domain.board.presentation.response;

import lombok.Builder;

@Builder
public class BoardPageResponse {
    private Long boardId;
    private String boardTitle;
    private String createdAt;
}
