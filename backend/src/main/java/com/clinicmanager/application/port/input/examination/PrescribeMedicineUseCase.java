package com.clinicmanager.application.port.input.examination;

import com.clinicmanager.application.dto.examination.PrescriptionDto;
import com.clinicmanager.application.dto.examination.PrescribeMedicineRequest;
import java.util.UUID;

public interface PrescribeMedicineUseCase {
    PrescriptionDto prescribe(UUID medicalSlipId, PrescribeMedicineRequest request);
}
