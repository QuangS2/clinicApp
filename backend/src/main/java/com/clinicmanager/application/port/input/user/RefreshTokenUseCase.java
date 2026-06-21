package com.clinicmanager.application.port.input.user;

import com.clinicmanager.application.dto.user.RefreshTokenRequest;
import com.clinicmanager.application.dto.user.TokenResponse;

public interface RefreshTokenUseCase {
    TokenResponse refresh(RefreshTokenRequest request);
}
