package team.bridgers.backend.domain.board.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.bridgers.backend.domain.board.domain.Vote;
import team.bridgers.backend.domain.board.domain.VoteRepository;

import java.time.LocalDate;


@Repository
@RequiredArgsConstructor
public class VoteRepositoryImpl implements VoteRepository {

    private final VoteJpaRepository voteJpaRepository;

    @Override
    public Vote save(Vote vote) {
        return voteJpaRepository.save(vote);
    }

    @Override
    public void delete(Vote vote) {
        voteJpaRepository.delete(vote);
    }

    @Override
    public boolean existsByUserIdAndBoardId(Long userId, Long boardId) {
        return voteJpaRepository.existsByUserIdAndBoardId(userId,boardId);
    }

    @Override
    public int countByUserIdAndVotedDate(Long userId, LocalDate today) {
        return voteJpaRepository.countByUserIdAndVotedDate(userId,today);
    }
}
