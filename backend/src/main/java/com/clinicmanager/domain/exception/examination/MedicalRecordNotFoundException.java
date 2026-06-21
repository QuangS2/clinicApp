package com.clinicmanager.domain.exception.examination;

import com.clinicmanager.domain.exception.BusinessException;

public class MedicalRecordNotFoundException extends BusinessException {
    public MedicalRecordNotFoundException(String message) {
        super(message);
    }
}
