package team.bridgers.backend.domain.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.bridgers.backend.domain.user.domain.User;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    boolean existsByNickname(String nickname);
    boolean existsByLoginId(String loginId);
    Optional<User> findByEmail(String email);
    Optional<User> findByLoginId(String loginId);
}
