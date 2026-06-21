package com.clinicmanager.presentation.response.examination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordResponse {
    private UUID id;
    private String diagnosis;
    private LocalDate createdDate;
    private String notes;
    private UUID medicalSlipId;
}
