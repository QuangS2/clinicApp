package com.clinicmanager.presentation.controller.examination;

import com.clinicmanager.application.dto.examination.CreateMedicalRecordRequest;
import com.clinicmanager.application.dto.examination.MedicalRecordDto;
import com.clinicmanager.application.port.input.examination.CreateMedicalRecordUseCase;
import com.clinicmanager.presentation.request.examination.SubmitMedicalRecordRequest;
import com.clinicmanager.presentation.response.ApiResponse;
import com.clinicmanager.presentation.response.examination.MedicalRecordResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/examinations")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final CreateMedicalRecordUseCase createMedicalRecordUseCase;

    @PostMapping("/{medicalSlipId}/medical-record")
    public ApiResponse<MedicalRecordResponse> create(
            @PathVariable UUID medicalSlipId,
            @Valid @RequestBody SubmitMedicalRecordRequest request) {

        CreateMedicalRecordRequest appRequest = CreateMedicalRecordRequest.builder()
                .notes(request.getNotes())
                .build();

        MedicalRecordDto resultDto = createMedicalRecordUseCase.create(medicalSlipId, appRequest);

        MedicalRecordResponse response = MedicalRecordResponse.builder()
                .id(resultDto.getId())
                .diagnosis(resultDto.getDiagnosis())
                .createdDate(resultDto.getCreatedDate())
                .notes(resultDto.getNotes())
                .medicalSlipId(resultDto.getMedicalSlipId())
                .build();

        return ApiResponse.success(response, "Cập nhật hồ sơ bệnh án thành công.");
    }
}
