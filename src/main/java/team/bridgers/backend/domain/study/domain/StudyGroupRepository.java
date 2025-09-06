package team.bridgers.backend.domain.study.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudyGroupRepository {
    boolean existsByName(String name);

    StudyGroup findByName(String name);

    StudyGroup findByType(GroupType type);

    StudyGroup findById(Long groupId);

    StudyGroup save(StudyGroup studyGroup);

    Page<StudyGroup> findAll(Pageable pageable);
}
