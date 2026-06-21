package com.clinicmanager.infrastructure.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    List<UserEntity> findByUsernameContainingIgnoreCase(String username);
}
