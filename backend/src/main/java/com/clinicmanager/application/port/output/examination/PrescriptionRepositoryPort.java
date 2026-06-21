package com.clinicmanager.application.port.output.examination;

import com.clinicmanager.domain.model.examination.Prescription;
import java.util.Optional;
import java.util.UUID;

public interface PrescriptionRepositoryPort {
    Prescription save(Prescription prescription);
    Optional<Prescription> findById(UUID id);
    Optional<Prescription> findByMedicalSlipId(UUID medicalSlipId);
    boolean existsByMedicalSlipId(UUID medicalSlipId);
}
