package com.clinicmanager.infrastructure.persistence.medicine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaMedicineRepository extends JpaRepository<MedicineEntity, String> {
    Optional<MedicineEntity> findByName(String name);
    boolean existsByName(String name);
    List<MedicineEntity> findByNameContainingIgnoreCase(String name);
}
