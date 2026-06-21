package com.clinicmanager.domain.exception.service;

import com.clinicmanager.domain.exception.BusinessException;

public class MedicalServiceNotFoundException extends BusinessException {
    public MedicalServiceNotFoundException(String message) {
        super(message);
    }
}
