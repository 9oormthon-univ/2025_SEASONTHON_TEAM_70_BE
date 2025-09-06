package team.bridgers.backend.domain.study.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import team.bridgers.backend.domain.study.domain.StudyGroup;
import team.bridgers.backend.domain.study.domain.UserStudyGroup;
import team.bridgers.backend.domain.user.domain.User;

import java.util.Optional;

public interface UserStudyGroupJpaRepository extends JpaRepository<UserStudyGroup, Long> {
    Page<UserStudyGroup> findByStudyGroup(StudyGroup studyGroup, Pageable pageable);

    Page<UserStudyGroup> findByUser(User user, Pageable pageable);
}
