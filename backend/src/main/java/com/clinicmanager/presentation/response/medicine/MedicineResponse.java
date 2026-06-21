package com.clinicmanager.presentation.response.medicine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineResponse {
    private UUID id;
    private String name;
    private String unit;
    private BigDecimal price;
    private int stockQuantity;
}
