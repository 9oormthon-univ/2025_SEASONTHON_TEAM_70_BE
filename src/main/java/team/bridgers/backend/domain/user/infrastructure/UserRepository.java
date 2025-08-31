package team.bridgers.backend.domain.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.bridgers.backend.domain.user.domain.User;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByNickname(String nickname);
    boolean existsByUserId(String userId);
    Optional<User> findByEmail(String email);
}
