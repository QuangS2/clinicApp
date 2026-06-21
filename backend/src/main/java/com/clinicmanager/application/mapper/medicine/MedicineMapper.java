package com.clinicmanager.application.mapper.medicine;

import com.clinicmanager.application.dto.medicine.MedicineDto;
import com.clinicmanager.application.dto.medicine.CreateMedicineRequest;
import com.clinicmanager.domain.model.medicine.Medicine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicineMapper {
    MedicineDto toDto(Medicine domain);

    @Mapping(target = "id", ignore = true)
    Medicine toDomain(CreateMedicineRequest request);
}
