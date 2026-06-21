package com.clinicmanager.domain.exception.examination;

import com.clinicmanager.domain.exception.BusinessException;

public class InvalidMedicalRecordDataException extends BusinessException {
    public InvalidMedicalRecordDataException(String message) {
        super(message);
    }
}
