package team.bridgers.backend.domain.study.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import team.bridgers.backend.domain.study.domain.StudyGroup;
import team.bridgers.backend.domain.study.domain.StudyGroupRepository;
import team.bridgers.backend.domain.study.dto.request.CreateStudyGroupRequest;
import team.bridgers.backend.domain.study.dto.response.CreateStudyGroupResponse;
import team.bridgers.backend.domain.study.dto.response.StudyGroupInfoResponse;
import team.bridgers.backend.domain.study.presentation.exception.DuplicateGroupNameException;

@Service
@RequiredArgsConstructor
public class StudyGroupService {
    private final StudyGroupRepository studyGroupRepository;

    public CreateStudyGroupResponse createStudyGroup(CreateStudyGroupRequest request) {
        if (studyGroupRepository.existsByName(request.name())) {
            throw new DuplicateGroupNameException();
        }

        StudyGroup studyGroup = StudyGroup.builder()
                .name(request.name())
                .content(request.content())
                .type(request.type())
                .build();

        studyGroupRepository.save(studyGroup);

        return CreateStudyGroupResponse.builder()
                .groupId(studyGroup.getId())
                .build();
    }

    public StudyGroupInfoResponse getStudyGroup(Long groupId) {
        StudyGroup studyGroup = studyGroupRepository.findById(groupId);

        return StudyGroupInfoResponse.builder()
                .name(studyGroup.getName())
                .content(studyGroup.getContent())
                .personnel(studyGroup.getPersonnel())
                .createdAt(studyGroup.getCreatedAt())
                .type(studyGroup.getType())
                .build();
    }

    public Page<StudyGroupInfoResponse> getAllStudyGroup(Pageable pageable) {
        Page<StudyGroup> studyGroups = studyGroupRepository.findAll(pageable);

        return studyGroups.map(studyGroup -> StudyGroupInfoResponse.builder()
                .name(studyGroup.getName())
                .content(studyGroup.getContent())
                .personnel(studyGroup.getPersonnel())
                .createdAt(studyGroup.getCreatedAt())
                .type(studyGroup.getType())
                .build());
    }
}
