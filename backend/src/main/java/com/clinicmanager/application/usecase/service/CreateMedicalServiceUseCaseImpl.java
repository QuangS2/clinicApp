package com.clinicmanager.application.usecase.service;

import com.clinicmanager.application.dto.service.CreateMedicalServiceRequest;
import com.clinicmanager.application.dto.service.MedicalServiceDto;
import com.clinicmanager.application.mapper.service.MedicalServiceMapper;
import com.clinicmanager.application.port.input.service.CreateMedicalServiceUseCase;
import com.clinicmanager.application.port.output.service.MedicalServiceRepositoryPort;
import com.clinicmanager.domain.exception.service.MedicalServiceAlreadyExistsException;
import com.clinicmanager.domain.model.service.MedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateMedicalServiceUseCaseImpl implements CreateMedicalServiceUseCase {

    private final MedicalServiceRepositoryPort repositoryPort;
    private final MedicalServiceMapper mapper;

    @Override
    @Transactional
    public MedicalServiceDto create(CreateMedicalServiceRequest request) {
        if (request.getName() != null && repositoryPort.existsByName(request.getName().trim())) {
            throw new MedicalServiceAlreadyExistsException("Dịch vụ với tên '" + request.getName().trim() + "' đã tồn tại.");
        }

        MedicalService domain = mapper.toDomain(request);
        MedicalService savedDomain = repositoryPort.save(domain);
        return mapper.toDto(savedDomain);
    }
}
