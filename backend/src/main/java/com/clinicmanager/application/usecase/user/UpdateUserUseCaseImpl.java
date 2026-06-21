package com.clinicmanager.application.usecase.user;

import com.clinicmanager.application.dto.user.UpdateUserRequest;
import com.clinicmanager.application.dto.user.UserDto;
import com.clinicmanager.application.mapper.user.UserMapper;
import com.clinicmanager.application.port.input.user.UpdateUserUseCase;
import com.clinicmanager.application.port.output.user.UserRepositoryPort;
import com.clinicmanager.domain.exception.user.UserAlreadyExistsException;
import com.clinicmanager.domain.exception.user.UserNotFoundException;
import com.clinicmanager.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserRepositoryPort repositoryPort;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto update(UUID id, UpdateUserRequest request) {
        User existing = repositoryPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy tài khoản người dùng với mã ID: " + id));

        String newUsername = request.getUsername() != null ? request.getUsername().trim() : "";
        if (!existing.getUsername().equalsIgnoreCase(newUsername)) {
            if (repositoryPort.existsByUsername(newUsername)) {
                throw new UserAlreadyExistsException("Tên đăng nhập '" + newUsername + "' đã được sử dụng.");
            }
        }

        String updatedPassword = existing.getPassword();
        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            updatedPassword = passwordEncoder.encode(request.getPassword());
        }

        User updatedDomain = new User(
                existing.getId(),
                newUsername,
                updatedPassword,
                request.getRole(),
                request.getStatus()
        );

        User saved = repositoryPort.save(updatedDomain);
        return mapper.toDto(saved);
    }
}
