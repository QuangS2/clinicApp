package com.clinicmanager.application.port.input.service;

import com.clinicmanager.application.dto.service.MedicalServiceDto;
import java.util.List;

public interface SearchMedicalServicesUseCase {
    List<MedicalServiceDto> search(String name);
}
