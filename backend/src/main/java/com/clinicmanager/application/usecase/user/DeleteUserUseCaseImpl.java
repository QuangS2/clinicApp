package com.clinicmanager.application.usecase.user;

import com.clinicmanager.application.port.input.user.DeleteUserUseCase;
import com.clinicmanager.application.port.output.user.UserRepositoryPort;
import com.clinicmanager.domain.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserRepositoryPort repositoryPort;

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repositoryPort.findById(id).isPresent()) {
            throw new UserNotFoundException("Không tìm thấy tài khoản người dùng với mã ID: " + id);
        }
        repositoryPort.delete(id);
    }
}
