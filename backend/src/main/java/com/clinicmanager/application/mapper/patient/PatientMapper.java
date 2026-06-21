package com.clinicmanager.application.mapper.patient;

import com.clinicmanager.application.dto.patient.PatientDto;
import com.clinicmanager.application.dto.patient.RegisterPatientRequest;
import com.clinicmanager.domain.model.patient.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDto toDto(Patient patient);

    @Mapping(target = "id", ignore = true)
    Patient toDomain(RegisterPatientRequest request);
}
