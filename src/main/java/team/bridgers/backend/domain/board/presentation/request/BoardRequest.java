package team.bridgers.backend.domain.board.presentation.request;

import lombok.Getter;
import team.bridgers.backend.domain.board.domain.BoardType;

@Getter
public class BoardRequest {
    private BoardType boardType;
    private String boardTitle;
    private String boardContent;
}
