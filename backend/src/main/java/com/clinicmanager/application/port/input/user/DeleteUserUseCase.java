package com.clinicmanager.application.port.input.user;

import java.util.UUID;

public interface DeleteUserUseCase {
    void delete(UUID id);
}
