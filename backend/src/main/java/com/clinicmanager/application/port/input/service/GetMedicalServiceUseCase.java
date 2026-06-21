package com.clinicmanager.application.port.input.service;

import com.clinicmanager.application.dto.service.MedicalServiceDto;
import java.util.UUID;

public interface GetMedicalServiceUseCase {
    MedicalServiceDto getById(UUID id);
}
