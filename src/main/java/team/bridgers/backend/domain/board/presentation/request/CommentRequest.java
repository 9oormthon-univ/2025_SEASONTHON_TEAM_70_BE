package team.bridgers.backend.domain.board.presentation.request;

import lombok.Builder;

@Builder
public record CommentRequest(
        Long BoardId,
        String commentContent
) {
}
