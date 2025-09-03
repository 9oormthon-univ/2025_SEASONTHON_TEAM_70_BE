package team.bridgers.backend.domain.user.domain;

import java.util.Optional;

public interface UserRepository {

    boolean existsByNickname(String nickname);

    boolean existsByLoginId(String loginId);

    User findByEmail(String email);

    User save(User user);

    User findByLoginId(String loginId);

    User findById(Long userId);
}
