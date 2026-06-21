package com.clinicmanager.application.usecase.examination;

import com.clinicmanager.application.dto.examination.PrescribeMedicineRequest;
import com.clinicmanager.application.dto.examination.PrescriptionDto;
import com.clinicmanager.application.mapper.examination.PrescriptionMapper;
import com.clinicmanager.application.port.input.examination.PrescribeMedicineUseCase;
import com.clinicmanager.application.port.output.admission.MedicalSlipRepositoryPort;
import com.clinicmanager.application.port.output.examination.PrescriptionRepositoryPort;
import com.clinicmanager.application.port.output.medicine.MedicineRepositoryPort;
import com.clinicmanager.domain.exception.admission.InvalidMedicalSlipDataException;
import com.clinicmanager.domain.exception.admission.MedicalSlipNotFoundException;
import com.clinicmanager.domain.exception.examination.InvalidPrescriptionDataException;
import com.clinicmanager.domain.exception.medicine.MedicineNotFoundException;
import com.clinicmanager.domain.model.admission.MedicalSlip;
import com.clinicmanager.domain.model.admission.MedicalSlipStatus;
import com.clinicmanager.domain.model.examination.Prescription;
import com.clinicmanager.domain.model.examination.PrescriptionItem;
import com.clinicmanager.domain.model.medicine.Medicine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrescribeMedicineUseCaseImpl implements PrescribeMedicineUseCase {

    private final MedicalSlipRepositoryPort medicalSlipRepositoryPort;
    private final MedicineRepositoryPort medicineRepositoryPort;
    private final PrescriptionRepositoryPort prescriptionRepositoryPort;
    private final PrescriptionMapper prescriptionMapper;

    @Override
    @Transactional
    public PrescriptionDto prescribe(UUID medicalSlipId, PrescribeMedicineRequest request) {
        MedicalSlip medicalSlip = medicalSlipRepositoryPort.findById(medicalSlipId)
                .orElseThrow(() -> new MedicalSlipNotFoundException("Phiếu khám không tồn tại."));

        if (medicalSlip.getStatus() != MedicalSlipStatus.EXAMINING) {
            throw new InvalidMedicalSlipDataException("Chỉ có thể kê đơn thuốc khi bệnh nhân đang được khám lâm sàng.");
        }

        if (medicalSlip.getDiagnosis() == null || medicalSlip.getDiagnosis().trim().isEmpty()) {
            throw new InvalidMedicalSlipDataException("Chỉ có thể kê đơn thuốc sau khi bệnh nhân đã được chẩn đoán bệnh.");
        }

        if (prescriptionRepositoryPort.existsByMedicalSlipId(medicalSlipId)) {
            throw new InvalidPrescriptionDataException("Phiếu khám này đã có đơn thuốc.");
        }

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new InvalidPrescriptionDataException("Đơn thuốc phải chứa ít nhất một loại thuốc.");
        }

        Set<UUID> medicineIds = new HashSet<>();
        List<PrescriptionItem> domainItems = new ArrayList<>();

        for (PrescribeMedicineRequest.PrescribeItem item : request.getItems()) {
            if (item.getMedicineId() == null) {
                throw new InvalidPrescriptionDataException("Mã thuốc không được để trống.");
            }
            if (medicineIds.contains(item.getMedicineId())) {
                throw new InvalidPrescriptionDataException("Không được kê trùng loại thuốc trong một đơn thuốc.");
            }
            medicineIds.add(item.getMedicineId());

            Medicine medicine = medicineRepositoryPort.findById(item.getMedicineId())
                    .orElseThrow(() -> new MedicineNotFoundException("Thuốc không tồn tại."));

            // Deduct stock quantity
            Medicine updatedMedicine = medicine.deductStock(item.getQuantity());
            medicineRepositoryPort.save(updatedMedicine);

            PrescriptionItem domainItem = new PrescriptionItem(
                    item.getMedicineId(),
                    item.getQuantity(),
                    item.getInstruction()
            );
            domainItems.add(domainItem);
        }

        Prescription prescription = new Prescription(
                null,
                LocalDate.now(),
                medicalSlipId,
                domainItems
        );

        Prescription savedPrescription = prescriptionRepositoryPort.save(prescription);
        return prescriptionMapper.toDto(savedPrescription);
    }
}
