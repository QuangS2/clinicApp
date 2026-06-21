package com.clinicmanager.application.port.input.examination;

import com.clinicmanager.application.dto.admission.MedicalSlipDto;
import com.clinicmanager.application.dto.examination.DiagnoseRequest;
import java.util.UUID;

public interface DiagnoseUseCase {
    MedicalSlipDto diagnose(UUID medicalSlipId, DiagnoseRequest request);
}
