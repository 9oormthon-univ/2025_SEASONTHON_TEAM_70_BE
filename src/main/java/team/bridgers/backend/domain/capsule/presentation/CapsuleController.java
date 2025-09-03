package team.bridgers.backend.domain.capsule.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.bridgers.backend.domain.capsule.application.CapsuleService;
import team.bridgers.backend.domain.capsule.dto.request.ChangeCapsuleVisibilityRequest;
import team.bridgers.backend.domain.capsule.dto.request.CreateCapsuleRequest;
import team.bridgers.backend.domain.capsule.dto.response.CapsuleInfoResponse;
import team.bridgers.backend.domain.capsule.dto.response.ChangeCapsuleVisibilityResponse;
import team.bridgers.backend.domain.capsule.dto.response.CreateCapsuleResponse;
import team.bridgers.backend.domain.capsule.dto.response.DeleteCapsuleResponse;
import team.bridgers.backend.global.annotation.MemberId;



@RestController
@RequestMapping("/capsules")
@RequiredArgsConstructor
public class CapsuleController {

    private final CapsuleService capsuleService;

    @PostMapping
    public ResponseEntity<CreateCapsuleResponse> createCapsule(@MemberId Long userId, @Valid @RequestBody CreateCapsuleRequest createCapsuleRequest) {
        CreateCapsuleResponse response = capsuleService.createCapsule(userId, createCapsuleRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{capsuleId}")
    public ResponseEntity<CapsuleInfoResponse> getCapsule(@PathVariable("capsuleId") Long capsuleId) {
        CapsuleInfoResponse response = capsuleService.getCapsule(capsuleId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<CapsuleInfoResponse>> getAllMyCapsule(@MemberId Long userId, Pageable pageable) {

        Page<CapsuleInfoResponse> response = capsuleService.getMyAllCapsule(userId, pageable);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/visible")
    public ResponseEntity<Page<CapsuleInfoResponse>> getAllVisibleCapsule(Pageable pageable) {
        Page<CapsuleInfoResponse> response = capsuleService.getAllVisibleCapsule(pageable);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{capsuleId}")
    public ResponseEntity<DeleteCapsuleResponse> deleteCapsule(@PathVariable("capsuleId") Long capsuleId, @MemberId Long userId) {
        DeleteCapsuleResponse response = capsuleService.deleteCapsule(userId, capsuleId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{capsuleId}")
    public ResponseEntity<ChangeCapsuleVisibilityResponse> changeCapsuleVisibility(@MemberId Long memberId, @PathVariable("capsuleId") Long capsuleId, ChangeCapsuleVisibilityRequest changeCapsuleVisibilityRequest) {
        ChangeCapsuleVisibilityResponse response = capsuleService.changeCapsuleVisibility(memberId, capsuleId, changeCapsuleVisibilityRequest.visibility());
        return ResponseEntity.ok(response);
    }

}
