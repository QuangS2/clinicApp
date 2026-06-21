package com.clinicmanager.application.port.input.user;

import com.clinicmanager.application.dto.user.CreateUserRequest;
import com.clinicmanager.application.dto.user.UserDto;

public interface CreateUserUseCase {
    UserDto create(CreateUserRequest request);
}
