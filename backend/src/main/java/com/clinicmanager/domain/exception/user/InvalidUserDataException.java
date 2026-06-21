package com.clinicmanager.domain.exception.user;

import com.clinicmanager.domain.exception.BusinessException;

public class InvalidUserDataException extends BusinessException {
    public InvalidUserDataException(String message) {
        super(message);
    }
}
