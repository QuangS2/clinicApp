package com.clinicmanager.application.usecase.examination;

import com.clinicmanager.application.dto.examination.CreateMedicalRecordRequest;
import com.clinicmanager.application.dto.examination.MedicalRecordDto;
import com.clinicmanager.application.mapper.examination.MedicalRecordMapper;
import com.clinicmanager.application.port.input.examination.CreateMedicalRecordUseCase;
import com.clinicmanager.application.port.output.admission.MedicalSlipRepositoryPort;
import com.clinicmanager.application.port.output.examination.MedicalRecordRepositoryPort;
import com.clinicmanager.domain.exception.admission.MedicalSlipNotFoundException;
import com.clinicmanager.domain.exception.examination.InvalidMedicalRecordDataException;
import com.clinicmanager.domain.model.admission.MedicalSlip;
import com.clinicmanager.domain.model.examination.MedicalRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateMedicalRecordUseCaseImpl implements CreateMedicalRecordUseCase {

    private final MedicalSlipRepositoryPort medicalSlipRepositoryPort;
    private final MedicalRecordRepositoryPort medicalRecordRepositoryPort;
    private final MedicalRecordMapper medicalRecordMapper;

    @Override
    @Transactional
    public MedicalRecordDto create(UUID medicalSlipId, CreateMedicalRecordRequest request) {
        MedicalSlip medicalSlip = medicalSlipRepositoryPort.findById(medicalSlipId)
                .orElseThrow(() -> new MedicalSlipNotFoundException("Phiếu khám không tồn tại."));

        if (medicalRecordRepositoryPort.existsByMedicalSlipId(medicalSlipId)) {
            throw new InvalidMedicalRecordDataException("Phiếu khám này đã được lập hồ sơ bệnh án.");
        }

        // Complete the medical slip (status -> COMPLETED)
        MedicalSlip completedSlip = medicalSlip.complete();
        medicalSlipRepositoryPort.save(completedSlip);

        // Create and save medical record
        MedicalRecord medicalRecord = new MedicalRecord(
                null,
                completedSlip.getDiagnosis(),
                LocalDate.now(),
                request.getNotes(),
                medicalSlipId
        );

        MedicalRecord savedRecord = medicalRecordRepositoryPort.save(medicalRecord);
        return medicalRecordMapper.toDto(savedRecord);
    }
}
