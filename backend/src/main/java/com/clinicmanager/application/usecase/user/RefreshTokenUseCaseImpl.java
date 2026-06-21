package com.clinicmanager.application.usecase.user;

import com.clinicmanager.application.dto.user.RefreshTokenRequest;
import com.clinicmanager.application.dto.user.TokenResponse;
import com.clinicmanager.application.port.input.user.RefreshTokenUseCase;
import com.clinicmanager.application.port.output.security.TokenServicePort;
import com.clinicmanager.application.port.output.user.UserRepositoryPort;
import com.clinicmanager.domain.exception.user.InvalidUserDataException;
import com.clinicmanager.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCaseImpl implements RefreshTokenUseCase {

    private final UserRepositoryPort repositoryPort;
    private final TokenServicePort tokenServicePort;

    @Override
    @Transactional(readOnly = true)
    public TokenResponse refresh(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        if (refreshToken == null || !tokenServicePort.validateToken(refreshToken)) {
            throw new InvalidUserDataException("Mã Refresh Token không hợp lệ hoặc đã hết hạn.");
        }

        String username = tokenServicePort.getUsernameFromToken(refreshToken);
        User user = repositoryPort.findByUsername(username)
                .orElseThrow(() -> new InvalidUserDataException("Tài khoản liên kết với token này không tồn tại."));

        if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            throw new InvalidUserDataException("Tài khoản đang bị tạm khóa.");
        }

        String newAccessToken = tokenServicePort.generateAccessToken(user.getUsername(), user.getRole());
        String newRefreshToken = tokenServicePort.generateRefreshToken(user.getUsername(), user.getRole());

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
