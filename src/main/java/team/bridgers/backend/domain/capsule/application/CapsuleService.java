package team.bridgers.backend.domain.capsule.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.bridgers.backend.domain.capsule.domain.Capsule;
import team.bridgers.backend.domain.capsule.domain.CapsuleRepository;
import team.bridgers.backend.domain.capsule.domain.Visibility;
import team.bridgers.backend.domain.capsule.dto.request.CreateCapsuleRequest;
import team.bridgers.backend.domain.capsule.dto.response.CapsuleInfoResponse;
import team.bridgers.backend.domain.capsule.dto.response.ChangeCapsuleVisibilityResponse;
import team.bridgers.backend.domain.capsule.dto.response.CreateCapsuleResponse;
import team.bridgers.backend.domain.capsule.dto.response.DeleteCapsuleResponse;
import team.bridgers.backend.domain.capsule.presentation.exception.NoDeleteAuthorityException;
import team.bridgers.backend.domain.capsule.presentation.exception.SameVisibilityException;
import team.bridgers.backend.domain.user.domain.User;
import team.bridgers.backend.domain.user.domain.UserRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CapsuleService {

    private final CapsuleRepository capsuleRepository;

    private final UserRepository userRepository;

    @Transactional
    public CreateCapsuleResponse createCapsule(Long userId, CreateCapsuleRequest createCapsuleRequest) {

        User user = userRepository.findById(userId);

        Capsule capsule = Capsule.builder()
                .title(createCapsuleRequest.title())
                .content(createCapsuleRequest.content())
                .user(user)
                .build();

        capsuleRepository.save(capsule);

        return CreateCapsuleResponse.builder()
                .capsuleId(capsule.getId())
                .build();

    }

    @Transactional(readOnly = true)
    public CapsuleInfoResponse getCapsule(Long capsuleId) {

        Capsule capsule = capsuleRepository.findById(capsuleId);

        return CapsuleInfoResponse.builder()
                .title(capsule.getTitle())
                .content(capsule.getContent())
                .createdAt(capsule.getCreatedAt())
                .build();

    }

    @Transactional(readOnly = true)
    public Page<CapsuleInfoResponse> getMyAllCapsule(Long userId ,Pageable pageable) {

        Page<Capsule> capsules = capsuleRepository.findAllByUserId(userId, pageable);

        return capsules.map(capsule -> CapsuleInfoResponse.builder()
                .title(capsule.getTitle())
                .content(capsule.getContent())
                .createdAt(capsule.getCreatedAt())
                .build());

    }

    @Transactional
    public DeleteCapsuleResponse deleteCapsule(Long userId, Long capsuleId) {

        Capsule capsule = capsuleRepository.findById(capsuleId);

        if(!Objects.equals(capsule.getId(), userId)) {
            throw new NoDeleteAuthorityException();
        }
        capsuleRepository.delete(capsule);

        return DeleteCapsuleResponse.builder()
                .capsuleId(capsule.getId())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<CapsuleInfoResponse> getAllVisibleCapsule(Pageable pageable) {

        Page<Capsule> capsules = capsuleRepository.findAllByVisibility(Visibility.VISIBLE, pageable);

        return capsules.map(capsule -> CapsuleInfoResponse.builder()
                .title(capsule.getTitle())
                .content(capsule.getContent())
                .createdAt(capsule.getCreatedAt())
                .build());

    }


    @Transactional
    public ChangeCapsuleVisibilityResponse changeCapsuleVisibility(Long capsuleId, Visibility visibility) {
        Capsule capsule = capsuleRepository.findById(capsuleId);

        if(capsule.getVisibility() == visibility) {
            throw new SameVisibilityException();
        }
        capsule.updateCapsule(visibility);

        return ChangeCapsuleVisibilityResponse.builder()
                .capsuleId(capsule.getId())
                .visibility(capsule.getVisibility())
                .build();
    }

}
