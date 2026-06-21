package com.clinicmanager.domain.exception.service;

import com.clinicmanager.domain.exception.BusinessException;

public class MedicalServiceAlreadyExistsException extends BusinessException {
    public MedicalServiceAlreadyExistsException(String message) {
        super(message);
    }
}
