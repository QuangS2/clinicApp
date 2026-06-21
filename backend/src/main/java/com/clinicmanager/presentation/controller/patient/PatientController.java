package com.clinicmanager.presentation.controller.patient;

import com.clinicmanager.application.dto.patient.PatientDto;
import com.clinicmanager.application.dto.patient.RegisterPatientRequest;
import com.clinicmanager.application.port.input.patient.RegisterPatientUseCase;
import com.clinicmanager.presentation.request.patient.CreatePatientRequest;
import com.clinicmanager.presentation.response.ApiResponse;
import com.clinicmanager.presentation.response.patient.PatientResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final RegisterPatientUseCase registerPatientUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PatientResponse> registerPatient(@Valid @RequestBody CreatePatientRequest request) {
        RegisterPatientRequest appRequest = RegisterPatientRequest.builder()
            .fullName(request.getFullName())
            .dob(request.getDob())
            .gender(request.getGender())
            .phone(request.getPhone())
            .address(request.getAddress())
            .email(request.getEmail())
            .build();

        PatientDto patientDto = registerPatientUseCase.register(appRequest);

        PatientResponse response = PatientResponse.builder()
            .id(patientDto.getId())
            .fullName(patientDto.getFullName())
            .dob(patientDto.getDob())
            .gender(patientDto.getGender())
            .phone(patientDto.getPhone())
            .address(patientDto.getAddress())
            .email(patientDto.getEmail())
            .build();

        return ApiResponse.success(response, "Đăng ký bệnh nhân thành công.");
    }
}
