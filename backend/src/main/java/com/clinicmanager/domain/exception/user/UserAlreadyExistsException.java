package com.clinicmanager.domain.exception.user;

import com.clinicmanager.domain.exception.BusinessException;

public class UserAlreadyExistsException extends BusinessException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
