package team.bridgers.backend.domain.study.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import team.bridgers.backend.domain.study.domain.GroupType;
import team.bridgers.backend.domain.study.domain.StudyGroup;
import team.bridgers.backend.domain.study.domain.StudyGroupRepository;
import team.bridgers.backend.domain.study.presentation.exception.GroupNotFoundException;

@RequiredArgsConstructor
@Repository
public class StudyGroupRepositoryImpl implements StudyGroupRepository {
    private final StudyGroupJpaRepository studyGroupJpaRepository;

    @Override
    public boolean existsByName(String name) {
        return studyGroupJpaRepository.existsByName(name);
    }

    @Override
    public StudyGroup findByName(String name) {
        return studyGroupJpaRepository.findByName(name)
                .orElseThrow(GroupNotFoundException::new);
    }

    @Override
    public StudyGroup findByType(GroupType type) {
        return studyGroupJpaRepository.findByType(type)
                .orElseThrow(GroupNotFoundException::new);
    }

    @Override
    public StudyGroup findById(Long groupId) {
        return studyGroupJpaRepository.findById(groupId)
                .orElseThrow(GroupNotFoundException::new);
    }

    @Override
    public StudyGroup save(StudyGroup studyGroup) {
        return studyGroupJpaRepository.save(studyGroup);
    }

    @Override
    public Page<StudyGroup> findAll(Pageable pageable) {
        return studyGroupJpaRepository.findAll(pageable);
    }
}
