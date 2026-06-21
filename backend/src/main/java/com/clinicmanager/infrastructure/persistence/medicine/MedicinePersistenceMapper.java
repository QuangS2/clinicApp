package com.clinicmanager.infrastructure.persistence.medicine;

import com.clinicmanager.domain.model.medicine.Medicine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MedicinePersistenceMapper {

    @Mapping(target = "id", expression = "java(domain.getId() != null ? domain.getId().toString() : null)")
    MedicineEntity toEntity(Medicine domain);

    default Medicine toDomain(MedicineEntity entity) {
        if (entity == null) {
            return null;
        }
        UUID uuid = entity.getId() != null ? UUID.fromString(entity.getId()) : null;
        return new Medicine(
                uuid,
                entity.getName(),
                entity.getUnit(),
                entity.getPrice(),
                entity.getStockQuantity()
        );
    }
}
