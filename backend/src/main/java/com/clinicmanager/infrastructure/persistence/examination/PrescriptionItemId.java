package com.clinicmanager.infrastructure.persistence.examination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItemId implements Serializable {
    private String prescription;
    private String medicineId;
}
