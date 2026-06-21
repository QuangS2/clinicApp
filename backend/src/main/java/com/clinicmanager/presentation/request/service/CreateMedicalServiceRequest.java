package com.clinicmanager.presentation.request.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMedicalServiceRequest {

    @NotBlank(message = "Tên dịch vụ không được để trống.")
    private String name;

    @NotNull(message = "Đơn giá không được để trống.")
    @DecimalMin(value = "0.0", message = "Đơn giá không được âm.")
    private BigDecimal price;

    private String description;
}
