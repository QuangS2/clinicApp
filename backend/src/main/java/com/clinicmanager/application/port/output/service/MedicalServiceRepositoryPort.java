package com.clinicmanager.application.port.output.service;

import com.clinicmanager.domain.model.service.MedicalService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MedicalServiceRepositoryPort {
    MedicalService save(MedicalService service);
    Optional<MedicalService> findById(UUID id);
    Optional<MedicalService> findByName(String name);
    boolean existsByName(String name);
    List<MedicalService> search(String name);
    void delete(UUID id);
}
