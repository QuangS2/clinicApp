package com.clinicmanager.infrastructure.persistence.patient;

import com.clinicmanager.domain.model.patient.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PatientPersistenceMapper {

    @Mapping(target = "id", expression = "java(domain.getId() != null ? domain.getId().toString() : null)")
    PatientEntity toEntity(Patient domain);

    default Patient toDomain(PatientEntity entity) {
        if (entity == null) {
            return null;
        }
        UUID uuid = entity.getId() != null ? UUID.fromString(entity.getId()) : null;
        return new Patient(
            uuid,
            entity.getFullName(),
            entity.getDob(),
            entity.getGender(),
            entity.getPhone(),
            entity.getAddress(),
            entity.getEmail()
        );
    }
}
