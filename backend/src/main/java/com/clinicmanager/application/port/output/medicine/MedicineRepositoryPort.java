package com.clinicmanager.application.port.output.medicine;

import com.clinicmanager.domain.model.medicine.Medicine;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MedicineRepositoryPort {
    Medicine save(Medicine medicine);
    Optional<Medicine> findById(UUID id);
    Optional<Medicine> findByName(String name);
    boolean existsByName(String name);
    List<Medicine> search(String name);
    void delete(UUID id);
}
