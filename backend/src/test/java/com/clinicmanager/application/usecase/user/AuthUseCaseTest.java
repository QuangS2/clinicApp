package com.clinicmanager.application.usecase.user;

import com.clinicmanager.application.dto.user.LoginRequest;
import com.clinicmanager.application.dto.user.RefreshTokenRequest;
import com.clinicmanager.application.dto.user.TokenResponse;
import com.clinicmanager.application.port.output.security.TokenServicePort;
import com.clinicmanager.application.port.output.user.UserRepositoryPort;
import com.clinicmanager.domain.exception.user.InvalidUserDataException;
import com.clinicmanager.domain.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUseCaseTest {

    @Mock
    private UserRepositoryPort repositoryPort;

    @Mock
    private TokenServicePort tokenServicePort;

    @Mock
    private PasswordEncoder passwordEncoder;

    private LoginUseCaseImpl loginUseCase;
    private RefreshTokenUseCaseImpl refreshTokenUseCase;

    private User activeUser;
    private User inactiveUser;

    @BeforeEach
    void setUp() {
        loginUseCase = new LoginUseCaseImpl(repositoryPort, tokenServicePort, passwordEncoder);
        refreshTokenUseCase = new RefreshTokenUseCaseImpl(repositoryPort, tokenServicePort);

        activeUser = new User(
                UUID.randomUUID(),
                "doctor_a",
                "$2a$10$encodedpassword",
                "BAC_SI",
                "ACTIVE"
        );

        inactiveUser = new User(
                UUID.randomUUID(),
                "doctor_b",
                "$2a$10$encodedpassword",
                "BAC_SI",
                "INACTIVE"
        );
    }

    @Test
    void login_Success() {
        LoginRequest request = LoginRequest.builder()
                .username("doctor_a")
                .password("password123")
                .build();

        when(repositoryPort.findByUsername("doctor_a")).thenReturn(Optional.of(activeUser));
        when(passwordEncoder.matches("password123", "$2a$10$encodedpassword")).thenReturn(true);
        when(tokenServicePort.generateAccessToken("doctor_a", "BAC_SI")).thenReturn("accessTokenVal");
        when(tokenServicePort.generateRefreshToken("doctor_a", "BAC_SI")).thenReturn("refreshTokenVal");

        TokenResponse result = loginUseCase.login(request);

        assertNotNull(result);
        assertEquals("accessTokenVal", result.getAccessToken());
        assertEquals("refreshTokenVal", result.getRefreshToken());
        assertEquals("doctor_a", result.getUsername());
        assertEquals("BAC_SI", result.getRole());
    }

    @Test
    void login_Failure_PasswordMismatch() {
        LoginRequest request = LoginRequest.builder()
                .username("doctor_a")
                .password("wrongpassword")
                .build();

        when(repositoryPort.findByUsername("doctor_a")).thenReturn(Optional.of(activeUser));
        when(passwordEncoder.matches("wrongpassword", "$2a$10$encodedpassword")).thenReturn(false);

        assertThrows(InvalidUserDataException.class, () -> loginUseCase.login(request));
    }

    @Test
    void login_Failure_UserInactive() {
        LoginRequest request = LoginRequest.builder()
                .username("doctor_b")
                .password("password123")
                .build();

        when(repositoryPort.findByUsername("doctor_b")).thenReturn(Optional.of(inactiveUser));
        when(passwordEncoder.matches("password123", "$2a$10$encodedpassword")).thenReturn(true);

        assertThrows(InvalidUserDataException.class, () -> loginUseCase.login(request));
    }

    @Test
    void refresh_Success() {
        RefreshTokenRequest request = RefreshTokenRequest.builder()
                .refreshToken("oldRefreshToken")
                .build();

        when(tokenServicePort.validateToken("oldRefreshToken")).thenReturn(true);
        when(tokenServicePort.getUsernameFromToken("oldRefreshToken")).thenReturn("doctor_a");
        when(repositoryPort.findByUsername("doctor_a")).thenReturn(Optional.of(activeUser));
        when(tokenServicePort.generateAccessToken("doctor_a", "BAC_SI")).thenReturn("newAccessToken");
        when(tokenServicePort.generateRefreshToken("doctor_a", "BAC_SI")).thenReturn("newRefreshToken");

        TokenResponse result = refreshTokenUseCase.refresh(request);

        assertNotNull(result);
        assertEquals("newAccessToken", result.getAccessToken());
        assertEquals("newRefreshToken", result.getRefreshToken());
    }

    @Test
    void refresh_Failure_InvalidToken() {
        RefreshTokenRequest request = RefreshTokenRequest.builder()
                .refreshToken("invalidToken")
                .build();

        when(tokenServicePort.validateToken("invalidToken")).thenReturn(false);

        assertThrows(InvalidUserDataException.class, () -> refreshTokenUseCase.refresh(request));
    }
}
