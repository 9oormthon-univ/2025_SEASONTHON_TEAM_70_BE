package team.bridgers.backend.domain.capsule.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.bridgers.backend.domain.capsule.domain.Capsule;
import team.bridgers.backend.domain.capsule.domain.Visibility;

import java.util.List;
import java.util.Optional;

@Repository
public interface CapsuleJpaRepository extends JpaRepository<Capsule, Long> {



    Optional<Capsule> findById(Long capsuleId);

    List<Capsule> findAllByUserId(Long userId);

    Optional<Capsule> findByTitle(String title);

    List<Capsule> findAllByVisibility(Visibility visibility);

    void delete(Capsule capsule);
}
