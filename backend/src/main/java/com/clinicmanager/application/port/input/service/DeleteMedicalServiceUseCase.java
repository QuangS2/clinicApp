package com.clinicmanager.application.port.input.service;

import java.util.UUID;

public interface DeleteMedicalServiceUseCase {
    void delete(UUID id);
}
