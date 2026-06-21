package com.clinicmanager.domain.model.examination;

import com.clinicmanager.domain.exception.examination.InvalidPrescriptionDataException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Prescription {
    private final UUID id;
    private final LocalDate prescriptionDate;
    private final UUID medicalSlipId;
    private final List<PrescriptionItem> items;

    public Prescription(UUID id, LocalDate prescriptionDate, UUID medicalSlipId, List<PrescriptionItem> items) {
        this.id = id != null ? id : UUID.randomUUID();
        this.prescriptionDate = prescriptionDate != null ? prescriptionDate : LocalDate.now();
        this.medicalSlipId = medicalSlipId;
        this.items = items;
        validate();
    }

    private void validate() {
        if (prescriptionDate == null) {
            throw new InvalidPrescriptionDataException("Ngày kê đơn không được để trống.");
        }
        if (medicalSlipId == null) {
            throw new InvalidPrescriptionDataException("Mã phiếu khám không được để trống.");
        }
        if (items == null || items.isEmpty()) {
            throw new InvalidPrescriptionDataException("Đơn thuốc phải chứa ít nhất một loại thuốc.");
        }
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getPrescriptionDate() {
        return prescriptionDate;
    }

    public UUID getMedicalSlipId() {
        return medicalSlipId;
    }

    public List<PrescriptionItem> getItems() {
        return items;
    }
}
