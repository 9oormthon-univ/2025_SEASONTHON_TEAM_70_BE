package team.bridgers.backend.domain.capsule.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.bridgers.backend.domain.capsule.application.CapsuleService;
import team.bridgers.backend.domain.capsule.dto.request.CreateCapsuleRequest;
import team.bridgers.backend.domain.capsule.dto.response.CapsuleInfoResponse;
import team.bridgers.backend.domain.capsule.dto.response.ChangeCapsuleVisibilityResponse;
import team.bridgers.backend.domain.capsule.dto.response.CreateCapsuleResponse;
import team.bridgers.backend.domain.capsule.dto.response.DeleteCapsuleResponse;
import team.bridgers.backend.global.annotation.MemberId;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<CapsuleInfoResponse> getCapsule(@PathVariable("id") Long capsuleId) {
        CapsuleInfoResponse response = capsuleService.getCapsule(capsuleId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/visible")
    public ResponseEntity<List<CapsuleInfoResponse>> getAllVisibleCapsule() {
        List<CapsuleInfoResponse> response = capsuleService.getAllVisibleCapsule();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteCapsuleResponse> deleteCapsule(@PathVariable("id") Long capsuleId, @MemberId Long userId) {
        DeleteCapsuleResponse response = capsuleService.deleteCapsule(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChangeCapsuleVisibilityResponse> changeCapsuleVisibility(@PathVariable("id") Long capsuleId) {
        ChangeCapsuleVisibilityResponse response = capsuleService.changeCapsuleVisibility(capsuleId);
        return ResponseEntity.ok(response);
    }

}
