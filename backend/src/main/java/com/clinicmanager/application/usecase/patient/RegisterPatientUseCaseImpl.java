package com.clinicmanager.application.usecase.patient;

import com.clinicmanager.application.dto.patient.PatientDto;
import com.clinicmanager.application.dto.patient.RegisterPatientRequest;
import com.clinicmanager.application.mapper.patient.PatientMapper;
import com.clinicmanager.application.port.input.patient.RegisterPatientUseCase;
import com.clinicmanager.application.port.output.patient.PatientRepositoryPort;
import com.clinicmanager.domain.exception.patient.PatientAlreadyExistsException;
import com.clinicmanager.domain.model.patient.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterPatientUseCaseImpl implements RegisterPatientUseCase {

    private final PatientRepositoryPort patientRepositoryPort;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientDto register(RegisterPatientRequest request) {
        if (patientRepositoryPort.existsByPhone(request.getPhone())) {
            throw new PatientAlreadyExistsException("Số điện thoại đã tồn tại trong hệ thống.");
        }
        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            if (patientRepositoryPort.existsByEmail(request.getEmail().trim())) {
                throw new PatientAlreadyExistsException("Email đã tồn tại trong hệ thống.");
            }
        }

        Patient patient = patientMapper.toDomain(request);
        Patient savedPatient = patientRepositoryPort.save(patient);
        return patientMapper.toDto(savedPatient);
    }
}
