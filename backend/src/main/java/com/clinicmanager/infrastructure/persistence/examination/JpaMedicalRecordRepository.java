package com.clinicmanager.infrastructure.persistence.examination;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMedicalRecordRepository extends JpaRepository<MedicalRecordEntity, String> {
    boolean existsByMedicalSlipId(String medicalSlipId);
}
