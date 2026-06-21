package com.clinicmanager.application.usecase.medicine;

import com.clinicmanager.application.dto.medicine.MedicineDto;
import com.clinicmanager.application.mapper.medicine.MedicineMapper;
import com.clinicmanager.application.port.input.medicine.SearchMedicinesUseCase;
import com.clinicmanager.application.port.output.medicine.MedicineRepositoryPort;
import com.clinicmanager.domain.model.medicine.Medicine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchMedicinesUseCaseImpl implements SearchMedicinesUseCase {

    private final MedicineRepositoryPort repositoryPort;
    private final MedicineMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<MedicineDto> search(String name) {
        String searchName = name != null ? name.trim() : "";
        List<Medicine> medicines = repositoryPort.search(searchName);

        return medicines.stream()
                .limit(100)
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
