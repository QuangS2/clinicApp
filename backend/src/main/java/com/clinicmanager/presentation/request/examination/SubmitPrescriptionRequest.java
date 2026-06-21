package com.clinicmanager.presentation.request.examination;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitPrescriptionRequest {

    @NotEmpty(message = "Đơn thuốc phải chứa ít nhất một loại thuốc")
    @Valid
    private List<SubmitPrescriptionItemRequest> items;
}
