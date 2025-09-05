package team.bridgers.backend.domain.study.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.bridgers.backend.domain.study.domain.StudyGroup;
import team.bridgers.backend.domain.study.domain.StudyGroupRepository;
import team.bridgers.backend.domain.study.domain.UserStudyGroup;
import team.bridgers.backend.domain.study.domain.UserStudyGroupRepository;
import team.bridgers.backend.domain.study.dto.response.CreateUserStudyGroupResponse;
import team.bridgers.backend.domain.study.dto.response.StudyGroupInfoResponse;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.UserRepository;
import team.bridgers.backend.domain.user.dto.response.UserInfoResponse;

@Service
@RequiredArgsConstructor
public class UserStudyGroupService {
    private final UserStudyGroupRepository userStudyGroupRepository;
    private final UserRepository userRepository;
    private final StudyGroupRepository studyGroupRepository;

    @Transactional
    public CreateUserStudyGroupResponse joinGroup(Long groupId, Long userId) {
        User user = userRepository.findById(userId);
        StudyGroup studyGroup = studyGroupRepository.findById(groupId);

        UserStudyGroup userStudyGroup = UserStudyGroup.builder()
                .studyGroup(studyGroup)
                .user(user)
                .build();

        userStudyGroup.getStudyGroup().updatePersonnel();
        userStudyGroupRepository.save(userStudyGroup);


        return CreateUserStudyGroupResponse.builder()
                .userStudyGroupId(userStudyGroup.getId())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<UserInfoResponse> getUsersInGroup(Long groupId, Pageable pageable) {
        StudyGroup group = studyGroupRepository.findById(groupId);

        Page<UserStudyGroup> userStudyGroups = userStudyGroupRepository.findByStudyGroup(group, pageable);

        return userStudyGroups.map(userStudyGroup ->
                UserInfoResponse.builder()
                        .email(userStudyGroup.getUser().getEmail())
                        .nickname(userStudyGroup.getUser().getNickname())
                        .birthday(userStudyGroup.getUser().getBirthday())
                        .gender(userStudyGroup.getUser().getGender())
                        .type(userStudyGroup.getUser().getType())
                        .build());
    }

    @Transactional(readOnly = true)
    public Page<StudyGroupInfoResponse> getGroupsInUser(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId);

        Page<UserStudyGroup> userStudyGroups = userStudyGroupRepository.findByUser(user, pageable);

        return userStudyGroups.map(userStudyGroup -> StudyGroupInfoResponse.builder()
                .name(userStudyGroup.getStudyGroup().getName())
                .content(userStudyGroup.getStudyGroup().getContent())
                .personnel(userStudyGroup.getStudyGroup().getPersonnel())
                .createdAt(userStudyGroup.getStudyGroup().getCreatedAt())
                .type(userStudyGroup.getStudyGroup().getType())
                .build());
    }

}
