package team.bridgers.backend.domain.board.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.bridgers.backend.domain.board.domain.*;
import team.bridgers.backend.domain.board.presentation.exeption.AlreadyVotedException;
import team.bridgers.backend.domain.board.presentation.exeption.DailyVoteLimitExceededException;
import team.bridgers.backend.domain.board.presentation.response.VoteResponse;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.UserRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private static final int MAX_DAILY_VOTES = 3;

    @Transactional
    public VoteResponse voteForBoard(Long memberId, Long boardId) {
        // 일일 투표 제한 검증
        validateDailyVoteLimit(memberId);

        // 중복 투표 검증
        if (voteRepository.existsByUserIdAndBoardId(memberId, boardId)) {
            throw new AlreadyVotedException();
        }

        User user = userRepository.findById(memberId);
        Board board = boardRepository.findByBoardId(boardId);

        Vote vote = Vote.builder()
                .user(user)
                .board(board)
                .votedDate(LocalDate.now())
                .build();

        voteRepository.save(vote);

        return VoteResponse.builder()
                .voteId(vote.getVoteId())
                .build();
    }

    private void validateDailyVoteLimit(Long userId) {
        LocalDate today = LocalDate.now();
        int dailyVoteCount = voteRepository.countByUserIdAndVotedDate(userId, today);

        if (dailyVoteCount >= MAX_DAILY_VOTES) {
            throw new DailyVoteLimitExceededException();
        }
    }

}