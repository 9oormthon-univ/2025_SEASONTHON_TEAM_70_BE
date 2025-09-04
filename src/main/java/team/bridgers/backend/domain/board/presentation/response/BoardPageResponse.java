package team.bridgers.backend.domain.board.presentation.response;

import lombok.Builder;

@Builder
public record BoardPageResponse(
        Long boardId,
        String boardTitle,
        String createdAt,
        String userNickname
) {
}
