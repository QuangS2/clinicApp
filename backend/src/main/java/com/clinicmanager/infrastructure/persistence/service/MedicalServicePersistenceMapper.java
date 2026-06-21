package com.clinicmanager.infrastructure.persistence.service;

import com.clinicmanager.domain.model.service.MedicalService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MedicalServicePersistenceMapper {

    @Mapping(target = "id", expression = "java(domain.getId() != null ? domain.getId().toString() : null)")
    MedicalServiceEntity toEntity(MedicalService domain);

    default MedicalService toDomain(MedicalServiceEntity entity) {
        if (entity == null) {
            return null;
        }
        UUID uuid = entity.getId() != null ? UUID.fromString(entity.getId()) : null;
        return new MedicalService(
                uuid,
                entity.getName(),
                entity.getPrice(),
                entity.getDescription()
        );
    }
}
