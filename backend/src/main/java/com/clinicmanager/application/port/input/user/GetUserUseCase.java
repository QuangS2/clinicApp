package com.clinicmanager.application.port.input.user;

import com.clinicmanager.application.dto.user.UserDto;
import java.util.UUID;

public interface GetUserUseCase {
    UserDto getById(UUID id);
    UserDto getByUsername(String username);
}
