package com.clinicmanager.domain.exception.examination;

import com.clinicmanager.domain.exception.BusinessException;

public class InvalidPrescriptionDataException extends BusinessException {
    public InvalidPrescriptionDataException(String message) {
        super(message);
    }
}
