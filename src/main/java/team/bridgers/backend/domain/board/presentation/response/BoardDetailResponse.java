package team.bridgers.backend.domain.board.presentation.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoardDetailResponse {
    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private String createdAt;
    private String updatedAt;
    private String userNickname;
}
