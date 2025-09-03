package team.bridgers.backend.domain.capsule.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CapsuleRepository {

    Page<Capsule> findAllByUserId(Long userId, Pageable pageable);

    Page<Capsule> findAllByVisibility(Visibility visibility, Pageable pageable);

    void save(Capsule capsule);

    void delete(Capsule capsule);

    Capsule findById(Long capsuleId);
}
