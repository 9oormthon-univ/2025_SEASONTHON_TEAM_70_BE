package team.bridgers.backend.domain.board.presentation.response;

import lombok.Builder;

@Builder
public record VoteResponse(
    Long voteId
) {
}
