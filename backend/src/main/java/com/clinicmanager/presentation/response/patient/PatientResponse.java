package com.clinicmanager.presentation.response.patient;

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
public class PatientResponse {
    private UUID id;
    private String fullName;
    private LocalDate dob;
    private String gender;
    private String phone;
    private String address;
    private String email;
}
