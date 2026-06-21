package com.clinicmanager.application.dto.medicine;

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
    private String name;
    private String unit;
    private BigDecimal price;
    private int stockQuantity;
}
