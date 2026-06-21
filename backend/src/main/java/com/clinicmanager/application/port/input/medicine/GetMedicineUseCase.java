package com.clinicmanager.application.port.input.medicine;

import com.clinicmanager.application.dto.medicine.MedicineDto;
import java.util.UUID;

public interface GetMedicineUseCase {
    MedicineDto getById(UUID id);
}
