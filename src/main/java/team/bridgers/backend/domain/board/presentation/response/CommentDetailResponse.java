package team.bridgers.backend.domain.board.presentation.response;

import lombok.Builder;

@Builder
public record CommentDetailResponse(
        Long commentId,
        String commentContent,
        String createdAt,
        String updatedAt,
        String userNickname
) {
}
