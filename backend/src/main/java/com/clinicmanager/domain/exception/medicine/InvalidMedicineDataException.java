package com.clinicmanager.domain.exception.medicine;

import com.clinicmanager.domain.exception.BusinessException;

public class InvalidMedicineDataException extends BusinessException {
    public InvalidMedicineDataException(String message) {
        super(message);
    }
}
