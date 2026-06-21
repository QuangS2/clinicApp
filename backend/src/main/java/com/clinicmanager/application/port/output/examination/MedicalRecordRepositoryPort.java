package com.clinicmanager.application.port.output.examination;

import com.clinicmanager.domain.model.examination.MedicalRecord;
import java.util.Optional;
import java.util.UUID;

public interface MedicalRecordRepositoryPort {
    MedicalRecord save(MedicalRecord medicalRecord);
    boolean existsByMedicalSlipId(UUID medicalSlipId);
    Optional<MedicalRecord> findById(UUID id);
}
