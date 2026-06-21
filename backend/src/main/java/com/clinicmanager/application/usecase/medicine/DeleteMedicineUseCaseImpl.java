package com.clinicmanager.application.usecase.medicine;

import com.clinicmanager.application.port.input.medicine.DeleteMedicineUseCase;
import com.clinicmanager.application.port.output.medicine.MedicineRepositoryPort;
import com.clinicmanager.domain.exception.medicine.MedicineNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteMedicineUseCaseImpl implements DeleteMedicineUseCase {

    private final MedicineRepositoryPort repositoryPort;

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repositoryPort.findById(id).isPresent()) {
            throw new MedicineNotFoundException("Không tìm thấy thuốc với mã ID: " + id);
        }
        repositoryPort.delete(id);
    }
}
