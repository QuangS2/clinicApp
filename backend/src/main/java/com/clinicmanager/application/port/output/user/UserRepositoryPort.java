package com.clinicmanager.application.port.output.user;

import com.clinicmanager.domain.model.user.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    List<User> search(String username);
    void delete(UUID id);
}
