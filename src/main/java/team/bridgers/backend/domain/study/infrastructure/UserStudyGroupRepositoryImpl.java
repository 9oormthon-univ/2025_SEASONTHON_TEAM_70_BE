package team.bridgers.backend.domain.study.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import team.bridgers.backend.domain.study.domain.StudyGroup;
import team.bridgers.backend.domain.study.domain.UserStudyGroup;
import team.bridgers.backend.domain.study.domain.UserStudyGroupRepository;
import team.bridgers.backend.domain.study.presentation.exception.GroupNotFoundException;
import team.bridgers.backend.domain.user.domain.User;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserStudyGroupRepositoryImpl implements UserStudyGroupRepository {

    private final UserStudyGroupJpaRepository studyGroupJpaRepository;

    @Override
    public Page<UserStudyGroup> findByStudyGroup(StudyGroup studyGroup, Pageable pageable) {
        return studyGroupJpaRepository.findByStudyGroup(studyGroup, pageable);
    }

    @Override
    public Page<UserStudyGroup> findByUser(User user, Pageable pageable) {
        return studyGroupJpaRepository.findByUser(user, pageable);
    }

    @Override
    public UserStudyGroup save(UserStudyGroup userStudyGroup) {
        return studyGroupJpaRepository.save(userStudyGroup);
    }

    @Override
    public UserStudyGroup findById(Long userStudyGroupId) {
        return studyGroupJpaRepository.findById(userStudyGroupId)
                .orElseThrow(GroupNotFoundException::new);
    }
}
