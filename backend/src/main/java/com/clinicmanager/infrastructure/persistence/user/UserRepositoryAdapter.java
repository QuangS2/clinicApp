package com.clinicmanager.infrastructure.persistence.user;

import com.clinicmanager.application.port.output.user.UserRepositoryPort;
import com.clinicmanager.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final JpaUserRepository jpaRepository;
    private final UserPersistenceMapper persistenceMapper;

    @Override
    public User save(User user) {
        UserEntity entity = persistenceMapper.toEntity(user);
        UserEntity saved = jpaRepository.save(entity);
        return persistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(UUID id) {
        if (id == null) {
            return Optional.empty();
        }
        return jpaRepository.findById(id.toString()).map(persistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepository.findByUsername(username).map(persistenceMapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    @Override
    public List<User> search(String username) {
        List<UserEntity> entities = jpaRepository.findByUsernameContainingIgnoreCase(username);
        return entities.stream()
                .map(persistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        if (id != null) {
            jpaRepository.deleteById(id.toString());
        }
    }
}
