package com.clinicmanager.infrastructure.persistence.examination;

import com.clinicmanager.domain.model.examination.MedicalRecord;
import org.mapstruct.Mapper;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MedicalRecordPersistenceMapper {

    MedicalRecordEntity toEntity(MedicalRecord domain);

    MedicalRecord toDomain(MedicalRecordEntity entity);

    default String map(UUID value) {
        return value != null ? value.toString() : null;
    }

    default UUID map(String value) {
        return value != null ? UUID.fromString(value) : null;
    }
}
