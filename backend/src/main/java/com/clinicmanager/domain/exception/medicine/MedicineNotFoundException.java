package com.clinicmanager.domain.exception.medicine;

import com.clinicmanager.domain.exception.BusinessException;

public class MedicineNotFoundException extends BusinessException {
    public MedicineNotFoundException(String message) {
        super(message);
    }
}
