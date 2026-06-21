package com.clinicmanager.application.mapper.examination;

import com.clinicmanager.application.dto.examination.PrescriptionDto;
import com.clinicmanager.application.dto.examination.PrescriptionItemDto;
import com.clinicmanager.domain.model.examination.Prescription;
import com.clinicmanager.domain.model.examination.PrescriptionItem;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {
    PrescriptionDto toDto(Prescription prescription);
    PrescriptionItemDto toDto(PrescriptionItem item);
    List<PrescriptionItemDto> toDtoList(List<PrescriptionItem> items);
}
