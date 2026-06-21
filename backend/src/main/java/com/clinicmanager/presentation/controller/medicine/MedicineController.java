package com.clinicmanager.presentation.controller.medicine;

import com.clinicmanager.application.dto.medicine.CreateMedicineRequest;
import com.clinicmanager.application.dto.medicine.UpdateMedicineRequest;
import com.clinicmanager.application.dto.medicine.MedicineDto;
import com.clinicmanager.application.port.input.medicine.CreateMedicineUseCase;
import com.clinicmanager.application.port.input.medicine.UpdateMedicineUseCase;
import com.clinicmanager.application.port.input.medicine.GetMedicineUseCase;
import com.clinicmanager.application.port.input.medicine.DeleteMedicineUseCase;
import com.clinicmanager.application.port.input.medicine.SearchMedicinesUseCase;
import com.clinicmanager.presentation.response.ApiResponse;
import com.clinicmanager.presentation.response.medicine.MedicineResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
public class MedicineController {

    private final CreateMedicineUseCase createUseCase;
    private final UpdateMedicineUseCase updateUseCase;
    private final GetMedicineUseCase getUseCase;
    private final DeleteMedicineUseCase deleteUseCase;
    private final SearchMedicinesUseCase searchUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MedicineResponse> createMedicine(@Valid @RequestBody com.clinicmanager.presentation.request.medicine.CreateMedicineRequest request) {
        CreateMedicineRequest appRequest = CreateMedicineRequest.builder()
                .name(request.getName())
                .unit(request.getUnit())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .build();

        MedicineDto dto = createUseCase.create(appRequest);
        return ApiResponse.success(mapToResponse(dto), "Thêm thuốc mới vào danh mục thành công.");
    }

    @PutMapping("/{id}")
    public ApiResponse<MedicineResponse> updateMedicine(
            @PathVariable UUID id,
            @Valid @RequestBody com.clinicmanager.presentation.request.medicine.UpdateMedicineRequest request) {

        UpdateMedicineRequest appRequest = UpdateMedicineRequest.builder()
                .name(request.getName())
                .unit(request.getUnit())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .build();

        MedicineDto dto = updateUseCase.update(id, appRequest);
        return ApiResponse.success(mapToResponse(dto), "Cập nhật thông tin thuốc thành công.");
    }

    @GetMapping("/{id}")
    public ApiResponse<MedicineResponse> getMedicine(@PathVariable UUID id) {
        MedicineDto dto = getUseCase.getById(id);
        return ApiResponse.success(mapToResponse(dto), "Lấy thông tin chi tiết thuốc thành công.");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteMedicine(@PathVariable UUID id) {
        deleteUseCase.delete(id);
        return ApiResponse.success(null, "Xóa thuốc khỏi danh mục thành công.");
    }

    @GetMapping
    public ApiResponse<List<MedicineResponse>> searchMedicines(@RequestParam(required = false) String name) {
        List<MedicineDto> dtos = searchUseCase.search(name);
        List<MedicineResponse> responses = dtos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(responses, "Tra cứu danh sách thuốc thành công.");
    }

    private MedicineResponse mapToResponse(MedicineDto dto) {
        return MedicineResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .unit(dto.getUnit())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity())
                .build();
    }
}
