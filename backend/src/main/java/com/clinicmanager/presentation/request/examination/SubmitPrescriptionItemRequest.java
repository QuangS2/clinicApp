package com.clinicmanager.presentation.request.examination;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitPrescriptionItemRequest {

    @NotNull(message = "Mã thuốc không được để trống")
    private UUID medicineId;

    @Min(value = 1, message = "Số lượng thuốc phải lớn hơn 0")
    private int quantity;

    @NotBlank(message = "Liều dùng không được để trống")
    private String instruction;
}
