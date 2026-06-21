package com.clinicmanager.application.usecase.medicine;

import com.clinicmanager.application.dto.medicine.UpdateMedicineRequest;
import com.clinicmanager.application.dto.medicine.MedicineDto;
import com.clinicmanager.application.mapper.medicine.MedicineMapper;
import com.clinicmanager.application.port.input.medicine.UpdateMedicineUseCase;
import com.clinicmanager.application.port.output.medicine.MedicineRepositoryPort;
import com.clinicmanager.domain.exception.medicine.MedicineAlreadyExistsException;
import com.clinicmanager.domain.exception.medicine.MedicineNotFoundException;
import com.clinicmanager.domain.model.medicine.Medicine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateMedicineUseCaseImpl implements UpdateMedicineUseCase {

    private final MedicineRepositoryPort repositoryPort;
    private final MedicineMapper mapper;

    @Override
    @Transactional
    public MedicineDto update(UUID id, UpdateMedicineRequest request) {
        Medicine existing = repositoryPort.findById(id)
                .orElseThrow(() -> new MedicineNotFoundException("Không tìm thấy thuốc với mã ID: " + id));

        String newName = request.getName() != null ? request.getName().trim() : "";
        if (!existing.getName().equalsIgnoreCase(newName)) {
            if (repositoryPort.existsByName(newName)) {
                throw new MedicineAlreadyExistsException("Thuốc với tên '" + newName + "' đã tồn tại.");
            }
        }

        Medicine updatedDomain = new Medicine(
                existing.getId(),
                newName,
                request.getUnit(),
                request.getPrice(),
                request.getStockQuantity()
        );

        Medicine saved = repositoryPort.save(updatedDomain);
        return mapper.toDto(saved);
    }
}
