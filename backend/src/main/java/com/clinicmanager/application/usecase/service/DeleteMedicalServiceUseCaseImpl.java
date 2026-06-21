package com.clinicmanager.application.usecase.service;

import com.clinicmanager.application.port.input.service.DeleteMedicalServiceUseCase;
import com.clinicmanager.application.port.output.service.MedicalServiceRepositoryPort;
import com.clinicmanager.domain.exception.service.MedicalServiceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteMedicalServiceUseCaseImpl implements DeleteMedicalServiceUseCase {

    private final MedicalServiceRepositoryPort repositoryPort;

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repositoryPort.findById(id).isPresent()) {
            throw new MedicalServiceNotFoundException("Không tìm thấy dịch vụ khám với mã ID: " + id);
        }
        repositoryPort.delete(id);
    }
}
