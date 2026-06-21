package com.clinicmanager.application.usecase.user;

import com.clinicmanager.application.dto.user.UserDto;
import com.clinicmanager.application.mapper.user.UserMapper;
import com.clinicmanager.application.port.input.user.GetUserUseCase;
import com.clinicmanager.application.port.output.user.UserRepositoryPort;
import com.clinicmanager.domain.exception.user.UserNotFoundException;
import com.clinicmanager.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {

    private final UserRepositoryPort repositoryPort;
    private final UserMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public UserDto getById(UUID id) {
        User user = repositoryPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy tài khoản người dùng với mã ID: " + id));
        return mapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getByUsername(String username) {
        User user = repositoryPort.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy tài khoản với tên đăng nhập: " + username));
        return mapper.toDto(user);
    }
}
