package com.clinicmanager.domain.model.examination;

import com.clinicmanager.domain.exception.examination.InvalidPrescriptionDataException;
import java.util.UUID;

public class PrescriptionItem {
    private final UUID medicineId;
    private final int quantity;
    private final String instruction;

    public PrescriptionItem(UUID medicineId, int quantity, String instruction) {
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.instruction = instruction;
        validate();
    }

    private void validate() {
        if (medicineId == null) {
            throw new InvalidPrescriptionDataException("Mã thuốc không được để trống.");
        }
        if (quantity <= 0) {
            throw new InvalidPrescriptionDataException("Số lượng thuốc kê phải lớn hơn 0.");
        }
        if (instruction == null || instruction.trim().isEmpty()) {
            throw new InvalidPrescriptionDataException("Liều dùng/hướng dẫn sử dụng không được để trống.");
        }
    }

    public UUID getMedicineId() {
        return medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getInstruction() {
        return instruction;
    }
}
