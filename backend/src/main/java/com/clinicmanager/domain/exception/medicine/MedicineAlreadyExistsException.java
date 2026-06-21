package com.clinicmanager.domain.exception.medicine;

import com.clinicmanager.domain.exception.BusinessException;

public class MedicineAlreadyExistsException extends BusinessException {
    public MedicineAlreadyExistsException(String message) {
        super(message);
    }
}
