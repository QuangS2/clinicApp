package com.clinicmanager.application.port.input.patient;

import com.clinicmanager.application.dto.patient.PatientDto;
import com.clinicmanager.application.dto.patient.RegisterPatientRequest;

public interface RegisterPatientUseCase {
    PatientDto register(RegisterPatientRequest request);
}
