package team.bridgers.backend.domain.board.presentation.response;

import lombok.Builder;

@Builder
public record BoardDetailResponse(
        Long boardId,
         String boardTitle,
         String boardContent,
         String createdAt,
         String updatedAt,
         String userNickname
) {
}
