package com.clinicmanager.application.usecase.medicine;

import com.clinicmanager.application.dto.medicine.CreateMedicineRequest;
import com.clinicmanager.application.dto.medicine.UpdateMedicineRequest;
import com.clinicmanager.application.dto.medicine.MedicineDto;
import com.clinicmanager.application.mapper.medicine.MedicineMapper;
import com.clinicmanager.application.port.output.medicine.MedicineRepositoryPort;
import com.clinicmanager.domain.exception.medicine.InvalidMedicineDataException;
import com.clinicmanager.domain.exception.medicine.MedicineAlreadyExistsException;
import com.clinicmanager.domain.exception.medicine.MedicineNotFoundException;
import com.clinicmanager.domain.model.medicine.Medicine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicineUseCaseTest {

    @Mock
    private MedicineRepositoryPort repositoryPort;

    @Mock
    private MedicineMapper mapper;

    private CreateMedicineUseCaseImpl createUseCase;
    private UpdateMedicineUseCaseImpl updateUseCase;
    private GetMedicineUseCaseImpl getUseCase;
    private DeleteMedicineUseCaseImpl deleteUseCase;
    private SearchMedicinesUseCaseImpl searchUseCase;

    private Medicine medicine1;
    private MedicineDto medicineDto1;

    @BeforeEach
    void setUp() {
        createUseCase = new CreateMedicineUseCaseImpl(repositoryPort, mapper);
        updateUseCase = new UpdateMedicineUseCaseImpl(repositoryPort, mapper);
        getUseCase = new GetMedicineUseCaseImpl(repositoryPort, mapper);
        deleteUseCase = new DeleteMedicineUseCaseImpl(repositoryPort);
        searchUseCase = new SearchMedicinesUseCaseImpl(repositoryPort, mapper);

        medicine1 = new Medicine(
                UUID.randomUUID(),
                "Paracetamol 500mg",
                "Viên",
                new BigDecimal("2000.00"),
                1000
        );

        medicineDto1 = MedicineDto.builder()
                .id(medicine1.getId())
                .name("Paracetamol 500mg")
                .unit("Viên")
                .price(new BigDecimal("2000.00"))
                .stockQuantity(1000)
                .build();
    }

    @Test
    void create_Success() {
        CreateMedicineRequest request = CreateMedicineRequest.builder()
                .name("Paracetamol 500mg")
                .unit("Viên")
                .price(new BigDecimal("2000.00"))
                .stockQuantity(1000)
                .build();

        when(repositoryPort.existsByName("Paracetamol 500mg")).thenReturn(false);
        when(mapper.toDomain(request)).thenReturn(medicine1);
        when(repositoryPort.save(medicine1)).thenReturn(medicine1);
        when(mapper.toDto(medicine1)).thenReturn(medicineDto1);

        MedicineDto result = createUseCase.create(request);

        assertNotNull(result);
        assertEquals("Paracetamol 500mg", result.getName());
        verify(repositoryPort, times(1)).save(medicine1);
    }

    @Test
    void create_Failure_AlreadyExists() {
        CreateMedicineRequest request = CreateMedicineRequest.builder()
                .name("Paracetamol 500mg")
                .unit("Viên")
                .price(new BigDecimal("2000.00"))
                .stockQuantity(1000)
                .build();

        when(repositoryPort.existsByName("Paracetamol 500mg")).thenReturn(true);

        assertThrows(MedicineAlreadyExistsException.class, () -> createUseCase.create(request));
        verify(repositoryPort, never()).save(any());
    }

    @Test
    void update_Success() {
        UUID id = medicine1.getId();
        UpdateMedicineRequest request = UpdateMedicineRequest.builder()
                .name("Paracetamol 500mg Mới")
                .unit("Vỉ")
                .price(new BigDecimal("2500.00"))
                .stockQuantity(500)
                .build();

        Medicine updatedDomain = new Medicine(
                id,
                "Paracetamol 500mg Mới",
                "Vỉ",
                new BigDecimal("2500.00"),
                500
        );
        MedicineDto updatedDto = MedicineDto.builder()
                .id(id)
                .name("Paracetamol 500mg Mới")
                .unit("Vỉ")
                .price(new BigDecimal("2500.00"))
                .stockQuantity(500)
                .build();

        when(repositoryPort.findById(id)).thenReturn(Optional.of(medicine1));
        when(repositoryPort.existsByName("Paracetamol 500mg Mới")).thenReturn(false);
        when(repositoryPort.save(any(Medicine.class))).thenReturn(updatedDomain);
        when(mapper.toDto(any(Medicine.class))).thenReturn(updatedDto);

        MedicineDto result = updateUseCase.update(id, request);

        assertNotNull(result);
        assertEquals("Paracetamol 500mg Mới", result.getName());
        assertEquals("Vỉ", result.getUnit());
        assertEquals(new BigDecimal("2500.00"), result.getPrice());
        assertEquals(500, result.getStockQuantity());
    }

    @Test
    void update_Failure_NotFound() {
        UUID id = UUID.randomUUID();
        UpdateMedicineRequest request = UpdateMedicineRequest.builder()
                .name("Panadol")
                .unit("Viên")
                .price(new BigDecimal("100.0"))
                .stockQuantity(10)
                .build();

        when(repositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThrows(MedicineNotFoundException.class, () -> updateUseCase.update(id, request));
    }

    @Test
    void getById_Success() {
        UUID id = medicine1.getId();
        when(repositoryPort.findById(id)).thenReturn(Optional.of(medicine1));
        when(mapper.toDto(medicine1)).thenReturn(medicineDto1);

        MedicineDto result = getUseCase.getById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void getById_Failure_NotFound() {
        UUID id = UUID.randomUUID();
        when(repositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThrows(MedicineNotFoundException.class, () -> getUseCase.getById(id));
    }

    @Test
    void delete_Success() {
        UUID id = medicine1.getId();
        when(repositoryPort.findById(id)).thenReturn(Optional.of(medicine1));

        assertDoesNotThrow(() -> deleteUseCase.delete(id));
        verify(repositoryPort, times(1)).delete(id);
    }

    @Test
    void delete_Failure_NotFound() {
        UUID id = UUID.randomUUID();
        when(repositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThrows(MedicineNotFoundException.class, () -> deleteUseCase.delete(id));
        verify(repositoryPort, never()).delete(any());
    }

    @Test
    void search_Success_LimitsTo100() {
        List<Medicine> mockList = new ArrayList<>();
        for (int i = 0; i < 105; i++) {
            mockList.add(medicine1);
        }
        when(repositoryPort.search("Para")).thenReturn(mockList);
        when(mapper.toDto(any(Medicine.class))).thenReturn(medicineDto1);

        List<MedicineDto> result = searchUseCase.search("Para");

        assertNotNull(result);
        assertEquals(100, result.size());
        verify(repositoryPort, times(1)).search("Para");
    }
}
