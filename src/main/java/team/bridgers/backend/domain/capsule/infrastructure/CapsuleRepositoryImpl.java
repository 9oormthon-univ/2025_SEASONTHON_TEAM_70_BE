package team.bridgers.backend.domain.capsule.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import team.bridgers.backend.domain.capsule.domain.Capsule;
import team.bridgers.backend.domain.capsule.domain.CapsuleRepository;
import team.bridgers.backend.domain.capsule.domain.Visibility;
import team.bridgers.backend.domain.capsule.presentation.exception.CapsuleNotFoundException;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CapsuleRepositoryImpl implements CapsuleRepository {

    private final CapsuleJpaRepository capsuleJpaRepository;

    @Override
    public Page<Capsule> findAllByUserId(Long userId, Pageable pageable) { return  capsuleJpaRepository.findAllByUserId(userId, pageable); }

    @Override
    public Page<Capsule> findAllByVisibility(Visibility visibility, Pageable pageable) { return capsuleJpaRepository.findAllByVisibility(visibility, pageable); }

    @Override
    public void save(Capsule capsule) { capsuleJpaRepository.save(capsule); }

    @Override
    public void delete(Capsule capsule) { capsuleJpaRepository.delete(capsule); }

    @Override
    public Capsule findById(Long capsuleId) {

        Optional<Capsule> savedCapsule = capsuleJpaRepository.findById(capsuleId);

        if (savedCapsule.isPresent()) {
            return savedCapsule.get();
        }
        throw new CapsuleNotFoundException();
    }
}
