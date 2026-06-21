package com.clinicmanager.domain.exception.examination;

import com.clinicmanager.domain.exception.BusinessException;

public class PrescriptionNotFoundException extends BusinessException {
    public PrescriptionNotFoundException(String message) {
        super(message);
    }
}
