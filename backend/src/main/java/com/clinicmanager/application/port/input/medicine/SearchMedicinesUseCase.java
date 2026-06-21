package com.clinicmanager.application.port.input.medicine;

import com.clinicmanager.application.dto.medicine.MedicineDto;
import java.util.List;

public interface SearchMedicinesUseCase {
    List<MedicineDto> search(String name);
}
