package team.bridgers.backend.domain.study.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import team.bridgers.backend.domain.study.domain.GroupType;
import team.bridgers.backend.domain.study.domain.StudyGroup;

import java.util.Optional;

public interface StudyGroupJpaRepository extends JpaRepository<StudyGroup, Long> {
    boolean existsByName(String name);

    Optional<StudyGroup> findByName(String name);

    Optional<StudyGroup> findByType(GroupType type);

    Page<StudyGroup> findAll(Pageable pageable);
}
