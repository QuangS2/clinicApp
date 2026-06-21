package com.clinicmanager.application.usecase.service;

import com.clinicmanager.application.dto.service.UpdateMedicalServiceRequest;
import com.clinicmanager.application.dto.service.MedicalServiceDto;
import com.clinicmanager.application.mapper.service.MedicalServiceMapper;
import com.clinicmanager.application.port.input.service.UpdateMedicalServiceUseCase;
import com.clinicmanager.application.port.output.service.MedicalServiceRepositoryPort;
import com.clinicmanager.domain.exception.service.MedicalServiceAlreadyExistsException;
import com.clinicmanager.domain.exception.service.MedicalServiceNotFoundException;
import com.clinicmanager.domain.model.service.MedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateMedicalServiceUseCaseImpl implements UpdateMedicalServiceUseCase {

    private final MedicalServiceRepositoryPort repositoryPort;
    private final MedicalServiceMapper mapper;

    @Override
    @Transactional
    public MedicalServiceDto update(UUID id, UpdateMedicalServiceRequest request) {
        MedicalService existing = repositoryPort.findById(id)
                .orElseThrow(() -> new MedicalServiceNotFoundException("Không tìm thấy dịch vụ khám với mã ID: " + id));

        String newName = request.getName() != null ? request.getName().trim() : "";
        if (!existing.getName().equalsIgnoreCase(newName)) {
            if (repositoryPort.existsByName(newName)) {
                throw new MedicalServiceAlreadyExistsException("Dịch vụ với tên '" + newName + "' đã tồn tại.");
            }
        }

        MedicalService updatedDomain = new MedicalService(
                existing.getId(),
                newName,
                request.getPrice(),
                request.getDescription()
        );

        MedicalService saved = repositoryPort.save(updatedDomain);
        return mapper.toDto(saved);
    }
}
