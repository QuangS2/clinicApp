package com.clinicmanager.application.mapper.examination;

import com.clinicmanager.application.dto.examination.MedicalRecordDto;
import com.clinicmanager.domain.model.examination.MedicalRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {
    MedicalRecordDto toDto(MedicalRecord medicalRecord);
}
