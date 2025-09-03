package team.bridgers.backend.domain.user.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.UserRepository;
import team.bridgers.backend.domain.user.presentation.exception.UserNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public boolean existsByNickname(String nickname) {
        return userJpaRepository.existsByNickname(nickname);
    }

    @Override
    public boolean existsByLoginId(String loginId) {
        return userJpaRepository.existsByLoginId(loginId);
    }

    @Override
    public User findById(Long userId) {

        Optional<User> savedUser = userJpaRepository.findById(userId);

        if(savedUser.isPresent()) {
            return savedUser.get();
        }

        throw new UserNotFoundException();
    }

    @Override
    public User findByEmail(String email) {

        Optional<User> savedUser = userJpaRepository.findByEmail(email);

        if(savedUser.isPresent()) {
            return savedUser.get();
        }

        throw new UserNotFoundException();

    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public User findByLoginId(String loginId) {

        Optional<User> savedUser = userJpaRepository.findByLoginId(loginId);

        if(savedUser.isPresent()) {
            return savedUser.get();
        }

        throw new UserNotFoundException();
    }

}
