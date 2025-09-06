package team.bridgers.backend.domain.board.domain;

import java.time.LocalDate;

public interface VoteRepository {

    Vote save(Vote vote);

    void delete(Vote vote);

    boolean existsByUserIdAndBoardId(Long memberId, Long boardId);

    int countByUserIdAndVotedDate(Long userId, LocalDate today);
}
