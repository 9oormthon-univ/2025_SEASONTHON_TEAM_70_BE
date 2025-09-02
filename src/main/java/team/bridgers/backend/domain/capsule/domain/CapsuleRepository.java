package team.bridgers.backend.domain.capsule.domain;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CapsuleRepository {

    Optional<Capsule> findById(Long capsuleId);

    List<Capsule> findAllByUserId(Long userId);

    Optional<Capsule> findByTitle(String title);

    List<Capsule> findAllByVisibility(Visibility visibility);

    void save(Capsule capsule);

    void delete(Capsule capsule);
}
