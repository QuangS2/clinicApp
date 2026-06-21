package com.clinicmanager.infrastructure.persistence.service;

import com.clinicmanager.application.port.output.service.MedicalServiceRepositoryPort;
import com.clinicmanager.domain.model.service.MedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MedicalServiceRepositoryAdapter implements MedicalServiceRepositoryPort {

    private final JpaMedicalServiceRepository jpaRepository;
    private final MedicalServicePersistenceMapper persistenceMapper;

    @Override
    public MedicalService save(MedicalService service) {
        MedicalServiceEntity entity = persistenceMapper.toEntity(service);
        MedicalServiceEntity saved = jpaRepository.save(entity);
        return persistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<MedicalService> findById(UUID id) {
        if (id == null) {
            return Optional.empty();
        }
        return jpaRepository.findById(id.toString()).map(persistenceMapper::toDomain);
    }

    @Override
    public Optional<MedicalService> findByName(String name) {
        return jpaRepository.findByName(name).map(persistenceMapper::toDomain);
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public List<MedicalService> search(String name) {
        List<MedicalServiceEntity> entities = jpaRepository.findByNameContainingIgnoreCase(name);
        return entities.stream()
                .map(persistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        if (id != null) {
            jpaRepository.deleteById(id.toString());
        }
    }
}
