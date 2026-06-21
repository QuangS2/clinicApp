package com.clinicmanager.application.port.input.user;

import com.clinicmanager.application.dto.user.UserDto;
import java.util.List;

public interface SearchUsersUseCase {
    List<UserDto> search(String username);
}
