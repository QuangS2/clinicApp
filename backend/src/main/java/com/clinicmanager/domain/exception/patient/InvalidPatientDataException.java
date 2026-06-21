package com.clinicmanager.domain.exception.patient;

import com.clinicmanager.domain.exception.BusinessException;

public class InvalidPatientDataException extends BusinessException {
    public InvalidPatientDataException(String message) {
        super(message);
    }
}
