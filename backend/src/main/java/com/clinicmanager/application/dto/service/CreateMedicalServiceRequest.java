package com.clinicmanager.application.dto.service;

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
    private String name;
    private BigDecimal price;
    private String description;
}
