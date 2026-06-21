package com.clinicmanager.infrastructure.persistence.medicine;

import com.clinicmanager.application.port.output.medicine.MedicineRepositoryPort;
import com.clinicmanager.domain.model.medicine.Medicine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MedicineRepositoryAdapter implements MedicineRepositoryPort {

    private final JpaMedicineRepository jpaRepository;
    private final MedicinePersistenceMapper persistenceMapper;

    @Override
    public Medicine save(Medicine medicine) {
        MedicineEntity entity = persistenceMapper.toEntity(medicine);
        MedicineEntity saved = jpaRepository.save(entity);
        return persistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Medicine> findById(UUID id) {
        if (id == null) {
            return Optional.empty();
        }
        return jpaRepository.findById(id.toString()).map(persistenceMapper::toDomain);
    }

    @Override
    public Optional<Medicine> findByName(String name) {
        return jpaRepository.findByName(name).map(persistenceMapper::toDomain);
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public List<Medicine> search(String name) {
        List<MedicineEntity> entities = jpaRepository.findByNameContainingIgnoreCase(name);
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
