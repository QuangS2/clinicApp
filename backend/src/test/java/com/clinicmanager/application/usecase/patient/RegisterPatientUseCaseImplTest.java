package com.clinicmanager.application.usecase.patient;

import com.clinicmanager.application.dto.patient.PatientDto;
import com.clinicmanager.application.dto.patient.RegisterPatientRequest;
import com.clinicmanager.application.mapper.patient.PatientMapper;
import com.clinicmanager.application.port.output.patient.PatientRepositoryPort;
import com.clinicmanager.domain.exception.patient.PatientAlreadyExistsException;
import com.clinicmanager.domain.model.patient.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterPatientUseCaseImplTest {

    @Mock
    private PatientRepositoryPort patientRepositoryPort;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private RegisterPatientUseCaseImpl registerPatientUseCase;

    private RegisterPatientRequest validRequest;
    private Patient validPatient;
    private PatientDto expectedDto;

    @BeforeEach
    void setUp() {
        validRequest = RegisterPatientRequest.builder()
                .fullName("Nguyễn Văn A")
                .dob(LocalDate.of(1990, 1, 1))
                .gender("Nam")
                .phone("0987654321")
                .address("Hà Nội")
                .email("vana@example.com")
                .build();

        validPatient = new Patient(
                UUID.randomUUID(),
                "Nguyễn Văn A",
                LocalDate.of(1990, 1, 1),
                "Nam",
                "0987654321",
                "Hà Nội",
                "vana@example.com"
        );

        expectedDto = PatientDto.builder()
                .id(validPatient.getId())
                .fullName("Nguyễn Văn A")
                .dob(LocalDate.of(1990, 1, 1))
                .gender("Nam")
                .phone("0987654321")
                .address("Hà Nội")
                .email("vana@example.com")
                .build();
    }

    @Test
    void register_Success() {
        // Arrange
        when(patientRepositoryPort.existsByPhone(validRequest.getPhone())).thenReturn(false);
        when(patientRepositoryPort.existsByEmail(validRequest.getEmail())).thenReturn(false);
        when(patientMapper.toDomain(validRequest)).thenReturn(validPatient);
        when(patientRepositoryPort.save(any(Patient.class))).thenReturn(validPatient);
        when(patientMapper.toDto(validPatient)).thenReturn(expectedDto);

        // Act
        PatientDto result = registerPatientUseCase.register(validRequest);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDto.getId(), result.getId());
        assertEquals(expectedDto.getFullName(), result.getFullName());
        assertEquals(expectedDto.getPhone(), result.getPhone());
        assertEquals(expectedDto.getEmail(), result.getEmail());

        verify(patientRepositoryPort, times(1)).existsByPhone(validRequest.getPhone());
        verify(patientRepositoryPort, times(1)).existsByEmail(validRequest.getEmail());
        verify(patientRepositoryPort, times(1)).save(any(Patient.class));
    }

    @Test
    void register_ThrowsException_WhenPhoneExists() {
        // Arrange
        when(patientRepositoryPort.existsByPhone(validRequest.getPhone())).thenReturn(true);

        // Act & Assert
        PatientAlreadyExistsException exception = assertThrows(
                PatientAlreadyExistsException.class,
                () -> registerPatientUseCase.register(validRequest)
        );

        assertEquals("Số điện thoại đã tồn tại trong hệ thống.", exception.getMessage());
        verify(patientRepositoryPort, times(1)).existsByPhone(validRequest.getPhone());
        verify(patientRepositoryPort, never()).existsByEmail(anyString());
        verify(patientRepositoryPort, never()).save(any(Patient.class));
    }

    @Test
    void register_ThrowsException_WhenEmailExists() {
        // Arrange
        when(patientRepositoryPort.existsByPhone(validRequest.getPhone())).thenReturn(false);
        when(patientRepositoryPort.existsByEmail(validRequest.getEmail())).thenReturn(true);

        // Act & Assert
        PatientAlreadyExistsException exception = assertThrows(
                PatientAlreadyExistsException.class,
                () -> registerPatientUseCase.register(validRequest)
        );

        assertEquals("Email đã tồn tại trong hệ thống.", exception.getMessage());
        verify(patientRepositoryPort, times(1)).existsByPhone(validRequest.getPhone());
        verify(patientRepositoryPort, times(1)).existsByEmail(validRequest.getEmail());
        verify(patientRepositoryPort, never()).save(any(Patient.class));
    }
}
