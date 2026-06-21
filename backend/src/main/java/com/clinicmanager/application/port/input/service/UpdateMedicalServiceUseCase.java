package com.clinicmanager.application.port.input.service;

import com.clinicmanager.application.dto.service.UpdateMedicalServiceRequest;
import com.clinicmanager.application.dto.service.MedicalServiceDto;
import java.util.UUID;

public interface UpdateMedicalServiceUseCase {
    MedicalServiceDto update(UUID id, UpdateMedicalServiceRequest request);
}
