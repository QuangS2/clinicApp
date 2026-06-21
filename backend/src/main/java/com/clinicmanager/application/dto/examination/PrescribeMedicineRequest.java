package com.clinicmanager.application.dto.examination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescribeMedicineRequest {
    private List<PrescribeItem> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrescribeItem {
        private UUID medicineId;
        private int quantity;
        private String instruction;
    }
}
