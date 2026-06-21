package com.clinicmanager.application.usecase.user;

import com.clinicmanager.application.dto.user.CreateUserRequest;
import com.clinicmanager.application.dto.user.UserDto;
import com.clinicmanager.application.mapper.user.UserMapper;
import com.clinicmanager.application.port.input.user.CreateUserUseCase;
import com.clinicmanager.application.port.output.user.UserRepositoryPort;
import com.clinicmanager.domain.exception.user.UserAlreadyExistsException;
import com.clinicmanager.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepositoryPort repositoryPort;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto create(CreateUserRequest request) {
        String username = request.getUsername() != null ? request.getUsername().trim() : "";
        if (repositoryPort.existsByUsername(username)) {
            throw new UserAlreadyExistsException("Tên đăng nhập '" + username + "' đã được sử dụng.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User domain = new User(
                null,
                username,
                encodedPassword,
                request.getRole(),
                request.getStatus()
        );

        User saved = repositoryPort.save(domain);
        return mapper.toDto(saved);
    }
}
