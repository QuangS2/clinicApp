package com.clinicmanager.domain.model.examination;

import com.clinicmanager.domain.exception.examination.InvalidMedicalRecordDataException;
import java.time.LocalDate;
import java.util.UUID;

public class MedicalRecord {
    private final UUID id;
    private final String diagnosis;
    private final LocalDate createdDate;
    private final String notes;
    private final UUID medicalSlipId;

    public MedicalRecord(UUID id, String diagnosis, LocalDate createdDate, String notes, UUID medicalSlipId) {
        this.id = id != null ? id : UUID.randomUUID();
        this.diagnosis = diagnosis;
        this.createdDate = createdDate != null ? createdDate : LocalDate.now();
        this.notes = notes;
        this.medicalSlipId = medicalSlipId;
        validate();
    }

    private void validate() {
        if (diagnosis == null || diagnosis.trim().isEmpty()) {
            throw new InvalidMedicalRecordDataException("Chẩn đoán bệnh không được để trống.");
        }
        if (createdDate == null) {
            throw new InvalidMedicalRecordDataException("Ngày lập hồ sơ bệnh án không được để trống.");
        }
        if (medicalSlipId == null) {
            throw new InvalidMedicalRecordDataException("Mã phiếu khám không được để trống.");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public String getNotes() {
        return notes;
    }

    public UUID getMedicalSlipId() {
        return medicalSlipId;
    }
}
