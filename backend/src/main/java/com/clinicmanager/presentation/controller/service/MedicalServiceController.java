package com.clinicmanager.presentation.controller.service;

import com.clinicmanager.application.dto.service.CreateMedicalServiceRequest;
import com.clinicmanager.application.dto.service.UpdateMedicalServiceRequest;
import com.clinicmanager.application.dto.service.MedicalServiceDto;
import com.clinicmanager.application.port.input.service.CreateMedicalServiceUseCase;
import com.clinicmanager.application.port.input.service.UpdateMedicalServiceUseCase;
import com.clinicmanager.application.port.input.service.GetMedicalServiceUseCase;
import com.clinicmanager.application.port.input.service.DeleteMedicalServiceUseCase;
import com.clinicmanager.application.port.input.service.SearchMedicalServicesUseCase;
import com.clinicmanager.presentation.response.ApiResponse;
import com.clinicmanager.presentation.response.service.MedicalServiceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class MedicalServiceController {

    private final CreateMedicalServiceUseCase createUseCase;
    private final UpdateMedicalServiceUseCase updateUseCase;
    private final GetMedicalServiceUseCase getUseCase;
    private final DeleteMedicalServiceUseCase deleteUseCase;
    private final SearchMedicalServicesUseCase searchUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MedicalServiceResponse> createService(@Valid @RequestBody com.clinicmanager.presentation.request.service.CreateMedicalServiceRequest request) {
        CreateMedicalServiceRequest appRequest = CreateMedicalServiceRequest.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .build();

        MedicalServiceDto dto = createUseCase.create(appRequest);
        return ApiResponse.success(mapToResponse(dto), "Thêm dịch vụ khám mới thành công.");
    }

    @PutMapping("/{id}")
    public ApiResponse<MedicalServiceResponse> updateService(
            @PathVariable UUID id,
            @Valid @RequestBody com.clinicmanager.presentation.request.service.UpdateMedicalServiceRequest request) {

        UpdateMedicalServiceRequest appRequest = UpdateMedicalServiceRequest.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .build();

        MedicalServiceDto dto = updateUseCase.update(id, appRequest);
        return ApiResponse.success(mapToResponse(dto), "Cập nhật dịch vụ khám thành công.");
    }

    @GetMapping("/{id}")
    public ApiResponse<MedicalServiceResponse> getService(@PathVariable UUID id) {
        MedicalServiceDto dto = getUseCase.getById(id);
        return ApiResponse.success(mapToResponse(dto), "Lấy thông tin chi tiết dịch vụ khám thành công.");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteService(@PathVariable UUID id) {
        deleteUseCase.delete(id);
        return ApiResponse.success(null, "Xóa dịch vụ khám thành công.");
    }

    @GetMapping
    public ApiResponse<List<MedicalServiceResponse>> searchServices(@RequestParam(required = false) String name) {
        List<MedicalServiceDto> dtos = searchUseCase.search(name);
        List<MedicalServiceResponse> responses = dtos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(responses, "Tra cứu danh sách dịch vụ khám thành công.");
    }

    private MedicalServiceResponse mapToResponse(MedicalServiceDto dto) {
        return MedicalServiceResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .build();
    }
}
