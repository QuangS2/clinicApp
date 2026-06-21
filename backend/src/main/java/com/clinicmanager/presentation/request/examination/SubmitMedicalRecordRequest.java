package com.clinicmanager.presentation.request.examination;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitMedicalRecordRequest {

    @Size(max = 1000, message = "Ghi chú dặn dò không được vượt quá 1000 ký tự.")
    private String notes;
}
