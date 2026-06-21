package com.clinicmanager.application.usecase.service;

import com.clinicmanager.application.dto.service.MedicalServiceDto;
import com.clinicmanager.application.mapper.service.MedicalServiceMapper;
import com.clinicmanager.application.port.input.service.SearchMedicalServicesUseCase;
import com.clinicmanager.application.port.output.service.MedicalServiceRepositoryPort;
import com.clinicmanager.domain.model.service.MedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchMedicalServicesUseCaseImpl implements SearchMedicalServicesUseCase {

    private final MedicalServiceRepositoryPort repositoryPort;
    private final MedicalServiceMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<MedicalServiceDto> search(String name) {
        String searchName = name != null ? name.trim() : "";
        List<MedicalService> services = repositoryPort.search(searchName);

        return services.stream()
                .limit(100)
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
