package com.clinicmanager.application.usecase.examination;

import com.clinicmanager.application.dto.admission.MedicalSlipDto;
import com.clinicmanager.application.dto.examination.DiagnoseRequest;
import com.clinicmanager.application.mapper.admission.MedicalSlipMapper;
import com.clinicmanager.application.port.output.admission.MedicalSlipRepositoryPort;
import com.clinicmanager.domain.exception.admission.InvalidMedicalSlipDataException;
import com.clinicmanager.domain.exception.admission.MedicalSlipNotFoundException;
import com.clinicmanager.domain.model.admission.MedicalSlip;
import com.clinicmanager.domain.model.admission.MedicalSlipStatus;
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
class DiagnoseUseCaseImplTest {

    @Mock
    private MedicalSlipRepositoryPort medicalSlipRepositoryPort;

    @Mock
    private MedicalSlipMapper medicalSlipMapper;

    @InjectMocks
    private DiagnoseUseCaseImpl diagnoseUseCase;

    private UUID medicalSlipId;
    private MedicalSlip examiningSlip;
    private MedicalSlip waitingSlip;
    private DiagnoseRequest request;

    @BeforeEach
    void setUp() {
        medicalSlipId = UUID.randomUUID();

        examiningSlip = new MedicalSlip(
                medicalSlipId,
                LocalDate.now(),
                MedicalSlipStatus.EXAMINING,
                UUID.randomUUID(),
                "Ho sốt", 80, 37.0, "120/80", 60.0, 165.0, null
        );

        waitingSlip = new MedicalSlip(
                medicalSlipId,
                LocalDate.now(),
                MedicalSlipStatus.WAITING,
                UUID.randomUUID(),
                null, null, null, null, null, null, null
        );

        request = DiagnoseRequest.builder()
                .diagnosis("Viêm họng cấp tính")
                .build();
    }

    @Test
    void diagnose_Success() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.of(examiningSlip));
        when(medicalSlipRepositoryPort.save(any(MedicalSlip.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MedicalSlipDto expectedDto = MedicalSlipDto.builder()
                .id(medicalSlipId)
                .examinationDate(LocalDate.now())
                .status("EXAMINING")
                .patientId(examiningSlip.getPatientId())
                .symptoms("Ho sốt")
                .pulse(80)
                .temperature(37.0)
                .bloodPressure("120/80")
                .weight(60.0)
                .height(165.0)
                .diagnosis("Viêm họng cấp tính")
                .build();
        when(medicalSlipMapper.toDto(any(MedicalSlip.class))).thenReturn(expectedDto);

        MedicalSlipDto result = diagnoseUseCase.diagnose(medicalSlipId, request);

        assertNotNull(result);
        assertEquals("Viêm họng cấp tính", result.getDiagnosis());
        verify(medicalSlipRepositoryPort, times(1)).save(any(MedicalSlip.class));
    }

    @Test
    void diagnose_ThrowsMedicalSlipNotFoundException_WhenSlipDoesNotExist() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.empty());

        assertThrows(MedicalSlipNotFoundException.class, () -> diagnoseUseCase.diagnose(medicalSlipId, request));

        verify(medicalSlipRepositoryPort, never()).save(any(MedicalSlip.class));
    }

    @Test
    void diagnose_ThrowsInvalidMedicalSlipDataException_WhenSlipIsNotExamining() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.of(waitingSlip));

        assertThrows(InvalidMedicalSlipDataException.class, () -> diagnoseUseCase.diagnose(medicalSlipId, request));

        verify(medicalSlipRepositoryPort, never()).save(any(MedicalSlip.class));
    }

    @Test
    void diagnose_ThrowsInvalidMedicalSlipDataException_WhenDiagnosisIsEmpty() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.of(examiningSlip));
        request.setDiagnosis("");

        assertThrows(InvalidMedicalSlipDataException.class, () -> diagnoseUseCase.diagnose(medicalSlipId, request));

        verify(medicalSlipRepositoryPort, never()).save(any(MedicalSlip.class));
    }
}
