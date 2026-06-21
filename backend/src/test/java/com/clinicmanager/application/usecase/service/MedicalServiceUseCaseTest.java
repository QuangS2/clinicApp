package com.clinicmanager.application.usecase.service;

import com.clinicmanager.application.dto.service.CreateMedicalServiceRequest;
import com.clinicmanager.application.dto.service.UpdateMedicalServiceRequest;
import com.clinicmanager.application.dto.service.MedicalServiceDto;
import com.clinicmanager.application.mapper.service.MedicalServiceMapper;
import com.clinicmanager.application.port.output.service.MedicalServiceRepositoryPort;
import com.clinicmanager.domain.exception.service.InvalidMedicalServiceDataException;
import com.clinicmanager.domain.exception.service.MedicalServiceAlreadyExistsException;
import com.clinicmanager.domain.exception.service.MedicalServiceNotFoundException;
import com.clinicmanager.domain.model.service.MedicalService;
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
class MedicalServiceUseCaseTest {

    @Mock
    private MedicalServiceRepositoryPort repositoryPort;

    @Mock
    private MedicalServiceMapper mapper;

    private CreateMedicalServiceUseCaseImpl createUseCase;
    private UpdateMedicalServiceUseCaseImpl updateUseCase;
    private GetMedicalServiceUseCaseImpl getUseCase;
    private DeleteMedicalServiceUseCaseImpl deleteUseCase;
    private SearchMedicalServicesUseCaseImpl searchUseCase;

    private MedicalService service1;
    private MedicalServiceDto serviceDto1;

    @BeforeEach
    void setUp() {
        createUseCase = new CreateMedicalServiceUseCaseImpl(repositoryPort, mapper);
        updateUseCase = new UpdateMedicalServiceUseCaseImpl(repositoryPort, mapper);
        getUseCase = new GetMedicalServiceUseCaseImpl(repositoryPort, mapper);
        deleteUseCase = new DeleteMedicalServiceUseCaseImpl(repositoryPort);
        searchUseCase = new SearchMedicalServicesUseCaseImpl(repositoryPort, mapper);

        service1 = new MedicalService(
                UUID.randomUUID(),
                "Khám Nội Tổng Quát",
                new BigDecimal("150000.00"),
                "Khám sức khỏe tổng quát nội khoa"
        );

        serviceDto1 = MedicalServiceDto.builder()
                .id(service1.getId())
                .name("Khám Nội Tổng Quát")
                .price(new BigDecimal("150000.00"))
                .description("Khám sức khỏe tổng quát nội khoa")
                .build();
    }

    @Test
    void create_Success() {
        CreateMedicalServiceRequest request = CreateMedicalServiceRequest.builder()
                .name("Khám Nội Tổng Quát")
                .price(new BigDecimal("150000.00"))
                .description("Khám sức khỏe tổng quát nội khoa")
                .build();

        when(repositoryPort.existsByName("Khám Nội Tổng Quát")).thenReturn(false);
        when(mapper.toDomain(request)).thenReturn(service1);
        when(repositoryPort.save(service1)).thenReturn(service1);
        when(mapper.toDto(service1)).thenReturn(serviceDto1);

        MedicalServiceDto result = createUseCase.create(request);

        assertNotNull(result);
        assertEquals("Khám Nội Tổng Quát", result.getName());
        verify(repositoryPort, times(1)).save(service1);
    }

    @Test
    void create_Failure_AlreadyExists() {
        CreateMedicalServiceRequest request = CreateMedicalServiceRequest.builder()
                .name("Khám Nội Tổng Quát")
                .price(new BigDecimal("150000.00"))
                .description("Khám sức khỏe tổng quát nội khoa")
                .build();

        when(repositoryPort.existsByName("Khám Nội Tổng Quát")).thenReturn(true);

        assertThrows(MedicalServiceAlreadyExistsException.class, () -> createUseCase.create(request));
        verify(repositoryPort, never()).save(any());
    }

    @Test
    void update_Success() {
        UUID id = service1.getId();
        UpdateMedicalServiceRequest request = UpdateMedicalServiceRequest.builder()
                .name("Khám Nội Tổng Quát Mới")
                .price(new BigDecimal("200000.00"))
                .description("Mô tả mới")
                .build();

        MedicalService updatedDomain = new MedicalService(
                id,
                "Khám Nội Tổng Quát Mới",
                new BigDecimal("200000.00"),
                "Mô tả mới"
        );
        MedicalServiceDto updatedDto = MedicalServiceDto.builder()
                .id(id)
                .name("Khám Nội Tổng Quát Mới")
                .price(new BigDecimal("200000.00"))
                .description("Mô tả mới")
                .build();

        when(repositoryPort.findById(id)).thenReturn(Optional.of(service1));
        when(repositoryPort.existsByName("Khám Nội Tổng Quát Mới")).thenReturn(false);
        when(repositoryPort.save(any(MedicalService.class))).thenReturn(updatedDomain);
        when(mapper.toDto(any(MedicalService.class))).thenReturn(updatedDto);

        MedicalServiceDto result = updateUseCase.update(id, request);

        assertNotNull(result);
        assertEquals("Khám Nội Tổng Quát Mới", result.getName());
        assertEquals(new BigDecimal("200000.00"), result.getPrice());
    }

    @Test
    void update_Failure_NotFound() {
        UUID id = UUID.randomUUID();
        UpdateMedicalServiceRequest request = UpdateMedicalServiceRequest.builder()
                .name("Khám Nội")
                .price(new BigDecimal("100.0"))
                .build();

        when(repositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThrows(MedicalServiceNotFoundException.class, () -> updateUseCase.update(id, request));
    }

    @Test
    void getById_Success() {
        UUID id = service1.getId();
        when(repositoryPort.findById(id)).thenReturn(Optional.of(service1));
        when(mapper.toDto(service1)).thenReturn(serviceDto1);

        MedicalServiceDto result = getUseCase.getById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void getById_Failure_NotFound() {
        UUID id = UUID.randomUUID();
        when(repositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThrows(MedicalServiceNotFoundException.class, () -> getUseCase.getById(id));
    }

    @Test
    void delete_Success() {
        UUID id = service1.getId();
        when(repositoryPort.findById(id)).thenReturn(Optional.of(service1));

        assertDoesNotThrow(() -> deleteUseCase.delete(id));
        verify(repositoryPort, times(1)).delete(id);
    }

    @Test
    void delete_Failure_NotFound() {
        UUID id = UUID.randomUUID();
        when(repositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThrows(MedicalServiceNotFoundException.class, () -> deleteUseCase.delete(id));
        verify(repositoryPort, never()).delete(any());
    }

    @Test
    void search_Success_LimitsTo100() {
        List<MedicalService> mockList = new ArrayList<>();
        for (int i = 0; i < 105; i++) {
            mockList.add(service1);
        }
        when(repositoryPort.search("Khám")).thenReturn(mockList);
        when(mapper.toDto(any(MedicalService.class))).thenReturn(serviceDto1);

        List<MedicalServiceDto> result = searchUseCase.search("Khám");

        assertNotNull(result);
        assertEquals(100, result.size());
        verify(repositoryPort, times(1)).search("Khám");
    }
}
