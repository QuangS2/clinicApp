package com.clinicmanager.application.port.input.medicine;

import com.clinicmanager.application.dto.medicine.UpdateMedicineRequest;
import com.clinicmanager.application.dto.medicine.MedicineDto;
import java.util.UUID;

public interface UpdateMedicineUseCase {
    MedicineDto update(UUID id, UpdateMedicineRequest request);
}
