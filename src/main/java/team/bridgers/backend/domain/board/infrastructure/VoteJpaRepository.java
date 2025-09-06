package team.bridgers.backend.domain.board.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.bridgers.backend.domain.board.domain.Vote;

import java.time.LocalDate;

public interface VoteJpaRepository extends JpaRepository<Vote, Long> {

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Vote v WHERE v.user.id = :userId AND v.board.boardId = :boardId")
    boolean existsByUserIdAndBoardId(Long userId, Long boardId);

    int countByUserIdAndVotedDate(Long user_id, LocalDate votedDate);
}
