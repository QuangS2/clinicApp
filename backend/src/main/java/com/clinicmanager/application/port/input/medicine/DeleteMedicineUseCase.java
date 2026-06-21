package com.clinicmanager.application.port.input.medicine;

import java.util.UUID;

public interface DeleteMedicineUseCase {
    void delete(UUID id);
}
