package com.clinicmanager.presentation.controller.user;

import com.clinicmanager.application.dto.user.LoginRequest;
import com.clinicmanager.application.dto.user.RefreshTokenRequest;
import com.clinicmanager.application.dto.user.TokenResponse;
import com.clinicmanager.application.port.input.user.LoginUseCase;
import com.clinicmanager.application.port.input.user.RefreshTokenUseCase;
import com.clinicmanager.presentation.response.ApiResponse;
import com.clinicmanager.presentation.response.user.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody com.clinicmanager.presentation.request.user.LoginRequest request) {
        LoginRequest appRequest = LoginRequest.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();

        TokenResponse tokenResponse = loginUseCase.login(appRequest);
        return ApiResponse.success(mapToResponse(tokenResponse), "Đăng nhập hệ thống thành công.");
    }

    @PostMapping("/refresh")
    public ApiResponse<LoginResponse> refresh(@Valid @RequestBody com.clinicmanager.presentation.request.user.RefreshTokenRequest request) {
        RefreshTokenRequest appRequest = RefreshTokenRequest.builder()
                .refreshToken(request.getRefreshToken())
                .build();

        TokenResponse tokenResponse = refreshTokenUseCase.refresh(appRequest);
        return ApiResponse.success(mapToResponse(tokenResponse), "Gia hạn mã token thành công.");
    }

    private LoginResponse mapToResponse(TokenResponse tokenResponse) {
        return LoginResponse.builder()
                .accessToken(tokenResponse.getAccessToken())
                .refreshToken(tokenResponse.getRefreshToken())
                .username(tokenResponse.getUsername())
                .role(tokenResponse.getRole())
                .build();
    }
}
