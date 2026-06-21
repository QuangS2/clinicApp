package com.clinicmanager.domain.exception.user;

import com.clinicmanager.domain.exception.BusinessException;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
