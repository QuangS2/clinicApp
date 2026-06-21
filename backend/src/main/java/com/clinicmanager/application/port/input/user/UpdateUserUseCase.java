package com.clinicmanager.application.port.input.user;

import com.clinicmanager.application.dto.user.UpdateUserRequest;
import com.clinicmanager.application.dto.user.UserDto;
import java.util.UUID;

public interface UpdateUserUseCase {
    UserDto update(UUID id, UpdateUserRequest request);
}
