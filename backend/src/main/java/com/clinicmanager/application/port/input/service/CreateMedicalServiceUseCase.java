package com.clinicmanager.application.port.input.service;

import com.clinicmanager.application.dto.service.CreateMedicalServiceRequest;
import com.clinicmanager.application.dto.service.MedicalServiceDto;

public interface CreateMedicalServiceUseCase {
    MedicalServiceDto create(CreateMedicalServiceRequest request);
}
