package com.clinicmanager.domain.exception.patient;

import com.clinicmanager.domain.exception.BusinessException;

public class PatientAlreadyExistsException extends BusinessException {
    public PatientAlreadyExistsException(String message) {
        super(message);
    }
}
