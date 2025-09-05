package team.bridgers.backend.domain.board.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.bridgers.backend.domain.board.domain.Board;
import team.bridgers.backend.domain.board.domain.BoardType;

import java.util.Optional;


public interface BoardJpaRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByBoardId(Long id);

    Page<Board> findAllByBoardType(BoardType type, Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.boardType = :boardType AND (b.boardTitle LIKE %:keyword% OR b.boardContent LIKE %:keyword%)")
    Page<Board> findByBoardTypeAndKeyword(@Param("boardType") BoardType boardType, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT b FROM Board b WHERE (b.boardTitle LIKE %:keyword% OR b.boardContent LIKE %:keyword%)")
    Page<Board> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
