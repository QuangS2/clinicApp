package com.clinicmanager.domain.exception.service;

import com.clinicmanager.domain.exception.BusinessException;

public class InvalidMedicalServiceDataException extends BusinessException {
    public InvalidMedicalServiceDataException(String message) {
        super(message);
    }
}
