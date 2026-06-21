package com.clinicmanager.application.port.input.medicine;

import com.clinicmanager.application.dto.medicine.CreateMedicineRequest;
import com.clinicmanager.application.dto.medicine.MedicineDto;

public interface CreateMedicineUseCase {
    MedicineDto create(CreateMedicineRequest request);
}
