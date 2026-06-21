package com.clinicmanager.application.usecase.service;

import com.clinicmanager.application.dto.service.MedicalServiceDto;
import com.clinicmanager.application.mapper.service.MedicalServiceMapper;
import com.clinicmanager.application.port.input.service.GetMedicalServiceUseCase;
import com.clinicmanager.application.port.output.service.MedicalServiceRepositoryPort;
import com.clinicmanager.domain.exception.service.MedicalServiceNotFoundException;
import com.clinicmanager.domain.model.service.MedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetMedicalServiceUseCaseImpl implements GetMedicalServiceUseCase {

    private final MedicalServiceRepositoryPort repositoryPort;
    private final MedicalServiceMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public MedicalServiceDto getById(UUID id) {
        MedicalService service = repositoryPort.findById(id)
                .orElseThrow(() -> new MedicalServiceNotFoundException("Không tìm thấy dịch vụ khám với mã ID: " + id));
        return mapper.toDto(service);
    }
}
