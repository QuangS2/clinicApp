package com.clinicmanager.infrastructure.persistence.examination;

import com.clinicmanager.domain.model.examination.Prescription;
import com.clinicmanager.domain.model.examination.PrescriptionItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PrescriptionPersistenceMapper {

    @Mapping(target = "id", expression = "java(domain.getId() != null ? domain.getId().toString() : null)")
    @Mapping(target = "medicalSlipId", expression = "java(domain.getMedicalSlipId() != null ? domain.getMedicalSlipId().toString() : null)")
    @Mapping(target = "items", ignore = true)
    PrescriptionEntity toEntity(Prescription domain);

    default PrescriptionEntity toEntityWithItems(Prescription domain) {
        if (domain == null) {
            return null;
        }
        PrescriptionEntity entity = toEntity(domain);
        if (domain.getItems() != null) {
            List<PrescriptionItemEntity> itemEntities = domain.getItems().stream()
                    .map(item -> PrescriptionItemEntity.builder()
                            .prescription(entity)
                            .medicineId(item.getMedicineId() != null ? item.getMedicineId().toString() : null)
                            .quantity(item.getQuantity())
                            .instruction(item.getInstruction())
                            .build())
                    .collect(Collectors.toList());
            entity.setItems(itemEntities);
        }
        return entity;
    }

    default Prescription toDomain(PrescriptionEntity entity) {
        if (entity == null) {
            return null;
        }
        UUID id = entity.getId() != null ? UUID.fromString(entity.getId()) : null;
        UUID medicalSlipId = entity.getMedicalSlipId() != null ? UUID.fromString(entity.getMedicalSlipId()) : null;
        
        List<PrescriptionItem> domainItems = List.of();
        if (entity.getItems() != null) {
            domainItems = entity.getItems().stream()
                    .map(itemEntity -> new PrescriptionItem(
                            itemEntity.getMedicineId() != null ? UUID.fromString(itemEntity.getMedicineId()) : null,
                            itemEntity.getQuantity(),
                            itemEntity.getInstruction()
                    ))
                    .collect(Collectors.toList());
        }

        return new Prescription(
                id,
                entity.getPrescriptionDate(),
                medicalSlipId,
                domainItems
        );
    }
}
