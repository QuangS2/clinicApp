package com.clinicmanager.application.port.input.examination;

import com.clinicmanager.application.dto.examination.CreateMedicalRecordRequest;
import com.clinicmanager.application.dto.examination.MedicalRecordDto;
import java.util.UUID;

public interface CreateMedicalRecordUseCase {
    MedicalRecordDto create(UUID medicalSlipId, CreateMedicalRecordRequest request);
}
