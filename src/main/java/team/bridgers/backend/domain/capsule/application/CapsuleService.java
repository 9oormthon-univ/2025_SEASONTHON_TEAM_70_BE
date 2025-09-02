package team.bridgers.backend.domain.capsule.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.bridgers.backend.domain.capsule.domain.Capsule;
import team.bridgers.backend.domain.capsule.domain.Visibility;
import team.bridgers.backend.domain.capsule.dto.request.CreateCapsuleRequest;
import team.bridgers.backend.domain.capsule.dto.response.CapsuleInfoResponse;
import team.bridgers.backend.domain.capsule.dto.response.ChangeCapsuleVisibilityResponse;
import team.bridgers.backend.domain.capsule.dto.response.CreateCapsuleResponse;
import team.bridgers.backend.domain.capsule.dto.response.DeleteCapsuleResponse;
import team.bridgers.backend.domain.capsule.infrastructure.CapsuleRepositoryImpl;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.infrastructure.UserRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CapsuleService {

    private final CapsuleRepositoryImpl capsuleRepository;

    private final UserRepositoryImpl userRepository;

    public CreateCapsuleResponse createCapsule(Long userId, CreateCapsuleRequest createCapsuleRequest) {

        Optional<User> user = userRepository.findByUserId(userId);

        Capsule capsule = Capsule.builder()
                .title(createCapsuleRequest.title())
                .content(createCapsuleRequest.content())
                .user(user.get())
                .build();

        capsuleRepository.save(capsule);

        Optional<Capsule> savedCapsule = capsuleRepository.findByTitle(createCapsuleRequest.title());

        return CreateCapsuleResponse.builder()
                .capsuleId(savedCapsule.get().getId())
                .build();

    }

    public CapsuleInfoResponse getCapsule(Long capsuleId) {

        Optional<Capsule> capsule = capsuleRepository.findById(capsuleId);

        return CapsuleInfoResponse.builder()
                .title(capsule.get().getTitle())
                .content(capsule.get().getContent())
                .createdAt(capsule.get().getCreatedAt())
                .build();

    }

    public List<CapsuleInfoResponse> getAllCapsule(Long userId) {
        List<Capsule> capsules = capsuleRepository.findAllByUserId(userId);
        return capsules.stream()
                .map(capsule -> CapsuleInfoResponse.builder()
                    .title(capsule.getTitle())
                    .content(capsule.getContent())
                    .createdAt(capsule.getCreatedAt())
                    .build())
                .collect(Collectors.toList());
    }

    public DeleteCapsuleResponse deleteCapsule(Long capsuleId) {

        Optional<Capsule> capsule = capsuleRepository.findById(capsuleId);

        capsuleRepository.delete(capsule.get());

        return DeleteCapsuleResponse.builder()
                .capsuleId(capsule.get().getId())
                .title(capsule.get().getTitle())
                .build();
    }

    public List<CapsuleInfoResponse> getAllVisibleCapsule() {

        List<Capsule> capsules = capsuleRepository.findAllByVisibility(Visibility.VISIBLE);

        return capsules.stream()
                .map(capsule -> CapsuleInfoResponse.builder()
                        .title(capsule.getTitle())
                        .content(capsule.getContent())
                        .createdAt(capsule.getCreatedAt())
                        .build())
                .toList();
    }

    public ChangeCapsuleVisibilityResponse changeCapsuleVisibility(Long capsuleId) {
        Optional<Capsule> capsule = capsuleRepository.findById(capsuleId);
        capsule.get().updateCapsule();
        return ChangeCapsuleVisibilityResponse.builder()
                .capsuleId(capsule.get().getId())
                .visibility(capsule.get().getVisibility())
                .build();
    }

}
