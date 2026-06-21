package com.clinicmanager.application.usecase.user;

import com.clinicmanager.application.dto.user.LoginRequest;
import com.clinicmanager.application.dto.user.TokenResponse;
import com.clinicmanager.application.port.input.user.LoginUseCase;
import com.clinicmanager.application.port.output.security.TokenServicePort;
import com.clinicmanager.application.port.output.user.UserRepositoryPort;
import com.clinicmanager.domain.exception.user.InvalidUserDataException;
import com.clinicmanager.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final UserRepositoryPort repositoryPort;
    private final TokenServicePort tokenServicePort;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest request) {
        String username = request.getUsername() != null ? request.getUsername().trim() : "";
        User user = repositoryPort.findByUsername(username)
                .orElseThrow(() -> new InvalidUserDataException("Tên đăng nhập hoặc mật khẩu không chính xác."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidUserDataException("Tên đăng nhập hoặc mật khẩu không chính xác.");
        }

        if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            throw new InvalidUserDataException("Tài khoản đang bị tạm khóa.");
        }

        String accessToken = tokenServicePort.generateAccessToken(user.getUsername(), user.getRole());
        String refreshToken = tokenServicePort.generateRefreshToken(user.getUsername(), user.getRole());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
