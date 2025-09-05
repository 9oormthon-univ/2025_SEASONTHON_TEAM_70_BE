package team.bridgers.backend.domain.study.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.bridgers.backend.domain.study.application.StudyGroupService;
import team.bridgers.backend.domain.study.application.UserStudyGroupService;
import team.bridgers.backend.domain.study.dto.request.CreateStudyGroupRequest;
import team.bridgers.backend.domain.study.dto.response.CreateStudyGroupResponse;
import team.bridgers.backend.domain.study.dto.response.CreateUserStudyGroupResponse;
import team.bridgers.backend.domain.study.dto.response.StudyGroupInfoResponse;
import team.bridgers.backend.domain.user.dto.response.UserInfoResponse;
import team.bridgers.backend.global.annotation.MemberId;

@RestController
@RequiredArgsConstructor
@RequestMapping("groups")
public class StudyGroupController {
    private final StudyGroupService studyGroupService;
    private final UserStudyGroupService userStudyGroupService;

    @PostMapping
    public ResponseEntity<CreateStudyGroupResponse> createStudyGroup(@RequestBody CreateStudyGroupRequest request, @MemberId Long userId) {
        CreateStudyGroupResponse response = studyGroupService.createStudyGroup(request, userId);

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

    @PostMapping("/{group_id}")
    public ResponseEntity<CreateUserStudyGroupResponse> join(@PathVariable("group_id")Long groupId, @MemberId Long userId) {
        CreateUserStudyGroupResponse response = userStudyGroupService.joinGroup(groupId, userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user-group/{group_id}")
    public ResponseEntity<Page<UserInfoResponse>> getUsersInGroup(@PathVariable("group_id")Long groupId, Pageable pageable) {
        Page<UserInfoResponse> responses = userStudyGroupService.getUsersInGroup(groupId, pageable);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user-group")
    public ResponseEntity<Page<StudyGroupInfoResponse>> getGroupsInUser(@MemberId Long userId, Pageable pageable) {
        Page<StudyGroupInfoResponse> responses = userStudyGroupService.getGroupsInUser(userId, pageable);

        return ResponseEntity.ok(responses);
    }
}
