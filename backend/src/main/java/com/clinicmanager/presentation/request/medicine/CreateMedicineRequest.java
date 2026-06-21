package com.clinicmanager.presentation.request.medicine;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
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
public class CreateMedicineRequest {

    @NotBlank(message = "Tên thuốc không được để trống.")
    private String name;

    @NotBlank(message = "Đơn vị tính không được để trống.")
    private String unit;

    @NotNull(message = "Đơn giá không được để trống.")
    @DecimalMin(value = "0.0", message = "Đơn giá không được âm.")
    private BigDecimal price;

    @Min(value = 0, message = "Số lượng tồn kho không được âm.")
    private int stockQuantity;
}
