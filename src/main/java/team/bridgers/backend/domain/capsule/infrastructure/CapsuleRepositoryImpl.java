package team.bridgers.backend.domain.capsule.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.bridgers.backend.domain.capsule.domain.Capsule;
import team.bridgers.backend.domain.capsule.domain.CapsuleRepository;
import team.bridgers.backend.domain.capsule.domain.Visibility;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CapsuleRepositoryImpl implements CapsuleRepository {

    private final CapsuleJpaRepository capsuleJpaRepository;

    @Override
    public Optional<Capsule> findById(Long capsuleId) { return capsuleJpaRepository.findById(capsuleId); }

    @Override
    public List<Capsule> findAllByUserId(Long userId) { return  capsuleJpaRepository.findAllByUserId(userId); }

    @Override
    public Optional<Capsule> findByTitle(String title) { return capsuleJpaRepository.findByTitle(title); }

    @Override
    public List<Capsule> findAllByVisibility(Visibility visibility) { return capsuleJpaRepository.findAllByVisibility(visibility); }

    @Override
    public void save(Capsule capsule) { capsuleJpaRepository.save(capsule); }

    @Override
    public void delete(Capsule capsule) { capsuleJpaRepository.delete(capsule); }
}
