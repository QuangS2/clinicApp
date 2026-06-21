package com.clinicmanager.application.dto.service;

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
public class MedicalServiceDto {
    private UUID id;
    private String name;
    private BigDecimal price;
    private String description;
}
