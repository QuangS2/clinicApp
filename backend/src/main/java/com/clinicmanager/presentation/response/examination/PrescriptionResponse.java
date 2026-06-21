package com.clinicmanager.presentation.response.examination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionResponse {
    private UUID id;
    private LocalDate prescriptionDate;
    private UUID medicalSlipId;
    private List<PrescriptionItemResponse> items;
}
