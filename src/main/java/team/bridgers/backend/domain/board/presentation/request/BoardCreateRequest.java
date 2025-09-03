package team.bridgers.backend.domain.board.presentation.request;

import lombok.Getter;

@Getter
public class BoardCreateRequest {
    private String boardTitle;
    private String boardContent;
}
