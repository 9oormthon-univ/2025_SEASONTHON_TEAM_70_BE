package team.bridgers.backend.domain.capsule.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import team.bridgers.backend.domain.capsule.domain.Capsule;
import team.bridgers.backend.domain.capsule.domain.Visibility;

public interface CapsuleJpaRepository extends JpaRepository<Capsule, Long> {

    Page<Capsule> findAllByUserId(Long userId, Pageable pageable);

    Page<Capsule> findAllByVisibility(Visibility visibility, Pageable pageable);

    void delete(Capsule capsule);
}
