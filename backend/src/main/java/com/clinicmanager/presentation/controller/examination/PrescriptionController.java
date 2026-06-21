package com.clinicmanager.presentation.controller.examination;

import com.clinicmanager.application.dto.examination.PrescribeMedicineRequest;
import com.clinicmanager.application.dto.examination.PrescriptionDto;
import com.clinicmanager.application.port.input.examination.PrescribeMedicineUseCase;
import com.clinicmanager.presentation.request.examination.SubmitPrescriptionRequest;
import com.clinicmanager.presentation.response.ApiResponse;
import com.clinicmanager.presentation.response.examination.PrescriptionResponse;
import com.clinicmanager.presentation.response.examination.PrescriptionItemResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/examinations")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescribeMedicineUseCase prescribeMedicineUseCase;

    @PostMapping("/{medicalSlipId}/prescription")
    public ApiResponse<PrescriptionResponse> prescribe(
            @PathVariable UUID medicalSlipId,
            @Valid @RequestBody SubmitPrescriptionRequest request) {

        List<PrescribeMedicineRequest.PrescribeItem> appItems = request.getItems().stream()
                .map(item -> PrescribeMedicineRequest.PrescribeItem.builder()
                        .medicineId(item.getMedicineId())
                        .quantity(item.getQuantity())
                        .instruction(item.getInstruction())
                        .build())
                .collect(Collectors.toList());

        PrescribeMedicineRequest appRequest = PrescribeMedicineRequest.builder()
                .items(appItems)
                .build();

        PrescriptionDto resultDto = prescribeMedicineUseCase.prescribe(medicalSlipId, appRequest);

        List<PrescriptionItemResponse> responseItems = resultDto.getItems().stream()
                .map(item -> PrescriptionItemResponse.builder()
                        .medicineId(item.getMedicineId())
                        .quantity(item.getQuantity())
                        .instruction(item.getInstruction())
                        .build())
                .collect(Collectors.toList());

        PrescriptionResponse response = PrescriptionResponse.builder()
                .id(resultDto.getId())
                .prescriptionDate(resultDto.getPrescriptionDate())
                .medicalSlipId(resultDto.getMedicalSlipId())
                .items(responseItems)
                .build();

        return ApiResponse.success(response, "Kê đơn thuốc thành công.");
    }
}
