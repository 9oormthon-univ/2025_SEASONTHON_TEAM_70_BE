package team.bridgers.backend.domain.user.domain;

import java.util.Optional;

public interface UserRepository {

    boolean existsByNickname(String nickname);

    boolean existsByLoginId(String loginId);

    Optional<User> findByUserId(Long userId);

    Optional<User> findByEmail(String email);

    User save(User user);

}
