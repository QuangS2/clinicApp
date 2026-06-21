package com.clinicmanager.application.usecase.examination;

import com.clinicmanager.application.dto.examination.PrescribeMedicineRequest;
import com.clinicmanager.application.dto.examination.PrescriptionDto;
import com.clinicmanager.application.dto.examination.PrescriptionItemDto;
import com.clinicmanager.application.mapper.examination.PrescriptionMapper;
import com.clinicmanager.application.port.output.admission.MedicalSlipRepositoryPort;
import com.clinicmanager.application.port.output.examination.PrescriptionRepositoryPort;
import com.clinicmanager.application.port.output.medicine.MedicineRepositoryPort;
import com.clinicmanager.domain.exception.admission.InvalidMedicalSlipDataException;
import com.clinicmanager.domain.exception.admission.MedicalSlipNotFoundException;
import com.clinicmanager.domain.exception.examination.InvalidPrescriptionDataException;
import com.clinicmanager.domain.exception.medicine.InvalidMedicineDataException;
import com.clinicmanager.domain.exception.medicine.MedicineNotFoundException;
import com.clinicmanager.domain.model.admission.MedicalSlip;
import com.clinicmanager.domain.model.admission.MedicalSlipStatus;
import com.clinicmanager.domain.model.examination.Prescription;
import com.clinicmanager.domain.model.medicine.Medicine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrescribeMedicineUseCaseImplTest {

    @Mock
    private MedicalSlipRepositoryPort medicalSlipRepositoryPort;

    @Mock
    private MedicineRepositoryPort medicineRepositoryPort;

    @Mock
    private PrescriptionRepositoryPort prescriptionRepositoryPort;

    @Mock
    private PrescriptionMapper prescriptionMapper;

    @InjectMocks
    private PrescribeMedicineUseCaseImpl prescribeMedicineUseCase;

    private UUID medicalSlipId;
    private UUID medicineId;
    private MedicalSlip validMedicalSlip;
    private Medicine validMedicine;
    private PrescribeMedicineRequest validRequest;

    @BeforeEach
    void setUp() {
        medicalSlipId = UUID.randomUUID();
        medicineId = UUID.randomUUID();

        validMedicalSlip = new MedicalSlip(
                medicalSlipId,
                LocalDate.now(),
                MedicalSlipStatus.EXAMINING,
                UUID.randomUUID(),
                "Ho sot",
                80,
                37.0,
                "120/80",
                60.0,
                165.0,
                "Viem phe quan"
        );

        validMedicine = new Medicine(
                medicineId,
                "Paracetamol 500mg",
                "Vien",
                BigDecimal.valueOf(1500),
                100
        );

        PrescribeMedicineRequest.PrescribeItem item = PrescribeMedicineRequest.PrescribeItem.builder()
                .medicineId(medicineId)
                .quantity(10)
                .instruction("Uong 1 vien sau khi an, ngay 2 lan")
                .build();

        validRequest = PrescribeMedicineRequest.builder()
                .items(List.of(item))
                .build();
    }

    @Test
    void prescribe_Success() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.of(validMedicalSlip));
        when(prescriptionRepositoryPort.existsByMedicalSlipId(medicalSlipId)).thenReturn(false);
        when(medicineRepositoryPort.findById(medicineId)).thenReturn(Optional.of(validMedicine));
        when(medicineRepositoryPort.save(any(Medicine.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(prescriptionRepositoryPort.save(any(Prescription.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PrescriptionItemDto itemDto = PrescriptionItemDto.builder()
                .medicineId(medicineId)
                .quantity(10)
                .instruction("Uong 1 vien sau khi an, ngay 2 lan")
                .build();

        PrescriptionDto expectedDto = PrescriptionDto.builder()
                .id(UUID.randomUUID())
                .medicalSlipId(medicalSlipId)
                .prescriptionDate(LocalDate.now())
                .items(List.of(itemDto))
                .build();

        when(prescriptionMapper.toDto(any(Prescription.class))).thenReturn(expectedDto);

        PrescriptionDto result = prescribeMedicineUseCase.prescribe(medicalSlipId, validRequest);

        assertNotNull(result);
        assertEquals(medicalSlipId, result.getMedicalSlipId());
        assertEquals(1, result.getItems().size());
        assertEquals(medicineId, result.getItems().get(0).getMedicineId());

        verify(medicalSlipRepositoryPort, times(1)).findById(medicalSlipId);
        verify(prescriptionRepositoryPort, times(1)).existsByMedicalSlipId(medicalSlipId);
        verify(medicineRepositoryPort, times(1)).findById(medicineId);
        verify(medicineRepositoryPort, times(1)).save(argThat(med -> med.getStockQuantity() == 90));
        verify(prescriptionRepositoryPort, times(1)).save(any(Prescription.class));
    }

    @Test
    void prescribe_ThrowsMedicalSlipNotFoundException_WhenSlipDoesNotExist() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.empty());

        assertThrows(MedicalSlipNotFoundException.class, () -> prescribeMedicineUseCase.prescribe(medicalSlipId, validRequest));

        verify(prescriptionRepositoryPort, never()).save(any(Prescription.class));
    }

    @Test
    void prescribe_ThrowsInvalidMedicalSlipDataException_WhenSlipIsNotExamining() {
        MedicalSlip waitingSlip = new MedicalSlip(
                medicalSlipId,
                LocalDate.now(),
                MedicalSlipStatus.WAITING,
                UUID.randomUUID(),
                null, null, null, null, null, null, null
        );

        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.of(waitingSlip));

        assertThrows(InvalidMedicalSlipDataException.class, () -> prescribeMedicineUseCase.prescribe(medicalSlipId, validRequest));

        verify(prescriptionRepositoryPort, never()).save(any(Prescription.class));
    }

    @Test
    void prescribe_ThrowsInvalidMedicalSlipDataException_WhenSlipHasNoDiagnosis() {
        MedicalSlip slipWithoutDiagnosis = new MedicalSlip(
                medicalSlipId,
                LocalDate.now(),
                MedicalSlipStatus.EXAMINING,
                UUID.randomUUID(),
                "Ho sot",
                80,
                37.0,
                "120/80",
                60.0,
                165.0,
                null
        );

        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.of(slipWithoutDiagnosis));

        assertThrows(InvalidMedicalSlipDataException.class, () -> prescribeMedicineUseCase.prescribe(medicalSlipId, validRequest));

        verify(prescriptionRepositoryPort, never()).save(any(Prescription.class));
    }

    @Test
    void prescribe_ThrowsInvalidPrescriptionDataException_WhenPrescriptionAlreadyExists() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.of(validMedicalSlip));
        when(prescriptionRepositoryPort.existsByMedicalSlipId(medicalSlipId)).thenReturn(true);

        assertThrows(InvalidPrescriptionDataException.class, () -> prescribeMedicineUseCase.prescribe(medicalSlipId, validRequest));

        verify(prescriptionRepositoryPort, never()).save(any(Prescription.class));
    }

    @Test
    void prescribe_ThrowsInvalidPrescriptionDataException_WhenItemsListIsNull() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.of(validMedicalSlip));
        when(prescriptionRepositoryPort.existsByMedicalSlipId(medicalSlipId)).thenReturn(false);

        PrescribeMedicineRequest request = PrescribeMedicineRequest.builder()
                .items(null)
                .build();

        assertThrows(InvalidPrescriptionDataException.class, () -> prescribeMedicineUseCase.prescribe(medicalSlipId, request));

        verify(prescriptionRepositoryPort, never()).save(any(Prescription.class));
    }

    @Test
    void prescribe_ThrowsInvalidPrescriptionDataException_WhenItemsListIsEmpty() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.of(validMedicalSlip));
        when(prescriptionRepositoryPort.existsByMedicalSlipId(medicalSlipId)).thenReturn(false);

        PrescribeMedicineRequest request = PrescribeMedicineRequest.builder()
                .items(Collections.emptyList())
                .build();

        assertThrows(InvalidPrescriptionDataException.class, () -> prescribeMedicineUseCase.prescribe(medicalSlipId, request));

        verify(prescriptionRepositoryPort, never()).save(any(Prescription.class));
    }

    @Test
    void prescribe_ThrowsInvalidPrescriptionDataException_WhenItemMedicineIdIsNull() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.of(validMedicalSlip));
        when(prescriptionRepositoryPort.existsByMedicalSlipId(medicalSlipId)).thenReturn(false);

        PrescribeMedicineRequest.PrescribeItem item = PrescribeMedicineRequest.PrescribeItem.builder()
                .medicineId(null)
                .quantity(5)
                .instruction("Uong")
                .build();

        PrescribeMedicineRequest request = PrescribeMedicineRequest.builder()
                .items(List.of(item))
                .build();

        assertThrows(InvalidPrescriptionDataException.class, () -> prescribeMedicineUseCase.prescribe(medicalSlipId, request));

        verify(prescriptionRepositoryPort, never()).save(any(Prescription.class));
    }

    @Test
    void prescribe_ThrowsInvalidPrescriptionDataException_WhenDuplicateMedicines() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.of(validMedicalSlip));
        when(prescriptionRepositoryPort.existsByMedicalSlipId(medicalSlipId)).thenReturn(false);
        when(medicineRepositoryPort.findById(medicineId)).thenReturn(Optional.of(validMedicine));
        when(medicineRepositoryPort.save(any(Medicine.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PrescribeMedicineRequest.PrescribeItem item1 = PrescribeMedicineRequest.PrescribeItem.builder()
                .medicineId(medicineId)
                .quantity(5)
                .instruction("Uong sang")
                .build();

        PrescribeMedicineRequest.PrescribeItem item2 = PrescribeMedicineRequest.PrescribeItem.builder()
                .medicineId(medicineId)
                .quantity(5)
                .instruction("Uong toi")
                .build();

        PrescribeMedicineRequest request = PrescribeMedicineRequest.builder()
                .items(List.of(item1, item2))
                .build();

        assertThrows(InvalidPrescriptionDataException.class, () -> prescribeMedicineUseCase.prescribe(medicalSlipId, request));

        verify(prescriptionRepositoryPort, never()).save(any(Prescription.class));
    }

    @Test
    void prescribe_ThrowsMedicineNotFoundException_WhenMedicineDoesNotExist() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.of(validMedicalSlip));
        when(prescriptionRepositoryPort.existsByMedicalSlipId(medicalSlipId)).thenReturn(false);
        when(medicineRepositoryPort.findById(medicineId)).thenReturn(Optional.empty());

        assertThrows(MedicineNotFoundException.class, () -> prescribeMedicineUseCase.prescribe(medicalSlipId, validRequest));

        verify(prescriptionRepositoryPort, never()).save(any(Prescription.class));
    }

    @Test
    void prescribe_ThrowsInvalidMedicineDataException_WhenStockIsInsufficient() {
        when(medicalSlipRepositoryPort.findById(medicalSlipId)).thenReturn(Optional.of(validMedicalSlip));
        when(prescriptionRepositoryPort.existsByMedicalSlipId(medicalSlipId)).thenReturn(false);

        Medicine lowStockMedicine = new Medicine(
                medicineId,
                "Paracetamol 500mg",
                "Vien",
                BigDecimal.valueOf(1500),
                5
        );

        when(medicineRepositoryPort.findById(medicineId)).thenReturn(Optional.of(lowStockMedicine));

        assertThrows(InvalidMedicineDataException.class, () -> prescribeMedicineUseCase.prescribe(medicalSlipId, validRequest));

        verify(prescriptionRepositoryPort, never()).save(any(Prescription.class));
    }
}
