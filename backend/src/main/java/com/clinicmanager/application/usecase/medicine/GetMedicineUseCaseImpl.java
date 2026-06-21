package com.clinicmanager.application.usecase.medicine;

import com.clinicmanager.application.dto.medicine.MedicineDto;
import com.clinicmanager.application.mapper.medicine.MedicineMapper;
import com.clinicmanager.application.port.input.medicine.GetMedicineUseCase;
import com.clinicmanager.application.port.output.medicine.MedicineRepositoryPort;
import com.clinicmanager.domain.exception.medicine.MedicineNotFoundException;
import com.clinicmanager.domain.model.medicine.Medicine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetMedicineUseCaseImpl implements GetMedicineUseCase {

    private final MedicineRepositoryPort repositoryPort;
    private final MedicineMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public MedicineDto getById(UUID id) {
        Medicine medicine = repositoryPort.findById(id)
                .orElseThrow(() -> new MedicineNotFoundException("Không tìm thấy thuốc với mã ID: " + id));
        return mapper.toDto(medicine);
    }
}
