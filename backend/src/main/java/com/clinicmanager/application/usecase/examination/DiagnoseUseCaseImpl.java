package com.clinicmanager.application.usecase.examination;

import com.clinicmanager.application.dto.admission.MedicalSlipDto;
import com.clinicmanager.application.dto.examination.DiagnoseRequest;
import com.clinicmanager.application.mapper.admission.MedicalSlipMapper;
import com.clinicmanager.application.port.input.examination.DiagnoseUseCase;
import com.clinicmanager.application.port.output.admission.MedicalSlipRepositoryPort;
import com.clinicmanager.domain.exception.admission.MedicalSlipNotFoundException;
import com.clinicmanager.domain.model.admission.MedicalSlip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiagnoseUseCaseImpl implements DiagnoseUseCase {

    private final MedicalSlipRepositoryPort medicalSlipRepositoryPort;
    private final MedicalSlipMapper medicalSlipMapper;

    @Override
    @Transactional
    public MedicalSlipDto diagnose(UUID medicalSlipId, DiagnoseRequest request) {
        MedicalSlip medicalSlip = medicalSlipRepositoryPort.findById(medicalSlipId)
                .orElseThrow(() -> new MedicalSlipNotFoundException("Phiếu khám không tồn tại."));

        MedicalSlip diagnosedSlip = medicalSlip.diagnose(request.getDiagnosis());

        MedicalSlip savedSlip = medicalSlipRepositoryPort.save(diagnosedSlip);
        return medicalSlipMapper.toDto(savedSlip);
    }
}
