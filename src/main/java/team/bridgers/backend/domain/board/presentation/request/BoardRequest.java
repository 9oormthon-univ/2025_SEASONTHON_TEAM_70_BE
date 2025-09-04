package team.bridgers.backend.domain.board.presentation.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import team.bridgers.backend.domain.board.domain.BoardType;

@Builder
public record BoardRequest(

        @NotBlank
        BoardType boardType,

        @NotBlank
        String boardTitle,

        @NotBlank
        String boardContent
) {
}
