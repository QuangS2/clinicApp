package com.clinicmanager.application.mapper.service;

import com.clinicmanager.application.dto.service.MedicalServiceDto;
import com.clinicmanager.application.dto.service.CreateMedicalServiceRequest;
import com.clinicmanager.domain.model.service.MedicalService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicalServiceMapper {
    MedicalServiceDto toDto(MedicalService domain);

    @Mapping(target = "id", ignore = true)
    MedicalService toDomain(CreateMedicalServiceRequest request);
}
