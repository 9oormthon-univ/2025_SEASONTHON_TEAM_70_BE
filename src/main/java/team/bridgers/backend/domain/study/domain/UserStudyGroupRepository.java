package team.bridgers.backend.domain.study.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team.bridgers.backend.domain.user.domain.User;

public interface UserStudyGroupRepository {
    Page<UserStudyGroup> findByStudyGroup(StudyGroup studyGroup, Pageable pageable);

    Page<UserStudyGroup> findByUser(User user, Pageable pageable);

    UserStudyGroup save(UserStudyGroup userStudyGroup);
}
