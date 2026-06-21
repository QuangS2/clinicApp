package com.clinicmanager.application.usecase.examination;

import com.clinicmanager.application.dto.examination.CreateMedicalRecordRequest;
import com.clinicmanager.application.dto.examination.MedicalRecordDto;
import com.clinicmanager.application.mapper.examination.MedicalRecordMapper;
import com.clinicmanager.application.port.output.admission.MedicalSlipRepositoryPort;
import com.clinicmanager.application.port.output.examination.MedicalRecordRepositoryPort;
import com.clinicmanager.domain.exception.admission.InvalidMedicalSlipDataException;
import com.clinicmanager.domain.exception.admission.MedicalSlipNotFoundException;
import com.clinicmanager.domain.exception.examination.InvalidMedicalRecordDataException;
import com.clinicmanager.domain.model.admission.MedicalSlip;
import com.clinicmanager.domain.model.admission.MedicalSlipStatus;
import com.clinicmanager.domain.model.examination.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateMedicalRecordUseCaseImplTest {

    @Mock
    private MedicalSlipRepositoryPort medicalSlipRepositoryPort;

    @Mock
    private MedicalRecordRepositoryPort medicalRecordRepositoryPort;

    @Mock
    private MedicalRecordMapper medicalRecordMapper;

    @InjectMocks
    private CreateMedicalRecordUseCaseImpl createMedicalRecordUseCase;

    private UUID medicalSlipId;
    private MedicalSlip examiningSlip;
    private MedicalSlip waitingSlip;
    private MedicalSlip slipWithoutDiagnosis;
    private CreateMedicalRecordRequest request;

    @BeforeEach
    void setUp() {
        medicalSlipId = UUID.randomUUID();

        examiningSlip = new MedicalSlip(
                medicalSlipId,
                LocalDate.now(),
                MedicalSlipStatus.EXAMINING,
                UUID.randomUUID(),
                "Sot cao",
                90,
                38.5,
                "120/80",
                55.0,
                160.0,
                "Viem amidan cap"
        );

        waitingSlip = new MedicalSlip(
                medicalSlipId,
                LocalDate.now(),
                MedicalSlipStatus.WAITING,
                UUID.randomUUID(),
                null, null, null, null, null, null, null
        );

        slipWithoutDiagnosis = new MedicalSlip(
                medicalSlipId,
                LocalDate.now(),
                MedicalSlipStatus.EXAMINING,
                UUID.randomUUID(),
                "Sot cao",
                90,
                38.5,
                "120/80",
                55.0,
                160.0,
                null
        );

        request = CreateMedicalRecordRequest.builder()
                .notes("Nghi ngoi, uong nhieu nuoc.")
                .build();
    }

    @Test
    void create_Success() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.of(examiningSlip));
        when(medicalRecordRepositoryPort.existsByMedicalSlipId(medicalSlipId)).thenReturn(false);
        when(medicalSlipRepositoryPort.save(any(MedicalSlip.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(medicalRecordRepositoryPort.save(any(MedicalRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MedicalRecordDto expectedDto = MedicalRecordDto.builder()
                .id(UUID.randomUUID())
                .diagnosis("Viem amidan cap")
                .createdDate(LocalDate.now())
                .notes("Nghi ngoi, uong nhieu nuoc.")
                .medicalSlipId(medicalSlipId)
                .build();

        when(medicalRecordMapper.toDto(any(MedicalRecord.class))).thenReturn(expectedDto);

        MedicalRecordDto result = createMedicalRecordUseCase.create(medicalSlipId, request);

        assertNotNull(result);
        assertEquals("Viem amidan cap", result.getDiagnosis());
        assertEquals(medicalSlipId, result.getMedicalSlipId());

        verify(medicalSlipRepositoryPort, times(1)).findById(medicalSlipId);
        verify(medicalRecordRepositoryPort, times(1)).existsByMedicalSlipId(medicalSlipId);
        verify(medicalSlipRepositoryPort, times(1)).save(argThat(slip -> slip.getStatus() == MedicalSlipStatus.COMPLETED));
        verify(medicalRecordRepositoryPort, times(1)).save(any(MedicalRecord.class));
    }

    @Test
    void create_ThrowsMedicalSlipNotFoundException_WhenSlipDoesNotExist() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.empty());

        assertThrows(MedicalSlipNotFoundException.class, () -> createMedicalRecordUseCase.create(medicalSlipId, request));

        verify(medicalRecordRepositoryPort, never()).save(any(MedicalRecord.class));
    }

    @Test
    void create_ThrowsInvalidMedicalSlipDataException_WhenSlipIsNotExamining() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.of(waitingSlip));

        assertThrows(InvalidMedicalSlipDataException.class, () -> createMedicalRecordUseCase.create(medicalSlipId, request));

        verify(medicalRecordRepositoryPort, never()).save(any(MedicalRecord.class));
    }

    @Test
    void create_ThrowsInvalidMedicalSlipDataException_WhenSlipHasNoDiagnosis() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.of(slipWithoutDiagnosis));

        assertThrows(InvalidMedicalSlipDataException.class, () -> createMedicalRecordUseCase.create(medicalSlipId, request));

        verify(medicalRecordRepositoryPort, never()).save(any(MedicalRecord.class));
    }

    @Test
    void create_ThrowsInvalidMedicalRecordDataException_WhenRecordAlreadyExists() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.of(examiningSlip));
        when(medicalRecordRepositoryPort.existsByMedicalSlipId(medicalSlipId)).thenReturn(true);

        assertThrows(InvalidMedicalRecordDataException.class, () -> createMedicalRecordUseCase.create(medicalSlipId, request));

        verify(medicalRecordRepositoryPort, never()).save(any(MedicalRecord.class));
    }
}
