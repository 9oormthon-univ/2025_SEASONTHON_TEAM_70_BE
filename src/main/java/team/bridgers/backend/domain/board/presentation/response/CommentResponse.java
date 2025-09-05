package team.bridgers.backend.domain.board.presentation.response;

import lombok.Builder;

@Builder
public record CommentResponse(
        Long commentId
) {
}
