package com.clinicmanager.application.usecase.medicine;

import com.clinicmanager.application.dto.medicine.CreateMedicineRequest;
import com.clinicmanager.application.dto.medicine.MedicineDto;
import com.clinicmanager.application.mapper.medicine.MedicineMapper;
import com.clinicmanager.application.port.input.medicine.CreateMedicineUseCase;
import com.clinicmanager.application.port.output.medicine.MedicineRepositoryPort;
import com.clinicmanager.domain.exception.medicine.MedicineAlreadyExistsException;
import com.clinicmanager.domain.model.medicine.Medicine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateMedicineUseCaseImpl implements CreateMedicineUseCase {

    private final MedicineRepositoryPort repositoryPort;
    private final MedicineMapper mapper;

    @Override
    @Transactional
    public MedicineDto create(CreateMedicineRequest request) {
        if (request.getName() != null && repositoryPort.existsByName(request.getName().trim())) {
            throw new MedicineAlreadyExistsException("Thuốc với tên '" + request.getName().trim() + "' đã tồn tại.");
        }

        Medicine domain = mapper.toDomain(request);
        Medicine savedDomain = repositoryPort.save(domain);
        return mapper.toDto(savedDomain);
    }
}
