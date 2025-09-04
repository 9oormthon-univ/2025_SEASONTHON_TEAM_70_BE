package team.bridgers.backend.domain.study.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.bridgers.backend.domain.study.application.StudyGroupService;
import team.bridgers.backend.domain.study.dto.request.CreateStudyGroupRequest;
import team.bridgers.backend.domain.study.dto.response.CreateStudyGroupResponse;
import team.bridgers.backend.domain.study.dto.response.StudyGroupInfoResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("groups")
public class StudyGroupController {
    private final StudyGroupService studyGroupService;

    @PostMapping
    public ResponseEntity<CreateStudyGroupResponse> createStudyGroup(@RequestBody CreateStudyGroupRequest request) {
        CreateStudyGroupResponse response = studyGroupService.createStudyGroup(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{group_id}")
    public ResponseEntity<StudyGroupInfoResponse> getStudyGroup(@PathVariable("group_id") Long groupId) {
        StudyGroupInfoResponse response = studyGroupService.getStudyGroup(groupId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<StudyGroupInfoResponse>> getAllStudyGroup(Pageable pageable) {
        Page<StudyGroupInfoResponse> response = studyGroupService.getAllStudyGroup(pageable);

        return ResponseEntity.ok(response);
    }
}
