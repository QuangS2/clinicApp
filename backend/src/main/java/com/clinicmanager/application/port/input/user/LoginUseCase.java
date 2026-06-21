package com.clinicmanager.application.port.input.user;

import com.clinicmanager.application.dto.user.LoginRequest;
import com.clinicmanager.application.dto.user.TokenResponse;

public interface LoginUseCase {
    TokenResponse login(LoginRequest request);
}
