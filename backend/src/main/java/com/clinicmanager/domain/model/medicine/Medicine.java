package com.clinicmanager.domain.model.medicine;

import com.clinicmanager.domain.exception.medicine.InvalidMedicineDataException;
import java.math.BigDecimal;
import java.util.UUID;

public class Medicine {
    private final UUID id;
    private final String name;
    private final String unit;
    private final BigDecimal price;
    private final int stockQuantity;

    public Medicine(UUID id, String name, String unit, BigDecimal price, int stockQuantity) {
        this.id = id != null ? id : UUID.randomUUID();
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.stockQuantity = stockQuantity;
        validate();
    }

    private void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidMedicineDataException("Tên thuốc không được để trống.");
        }
        if (unit == null || unit.trim().isEmpty()) {
            throw new InvalidMedicineDataException("Đơn vị tính không được để trống.");
        }
        if (price == null) {
            throw new InvalidMedicineDataException("Đơn giá thuốc không được để trống.");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidMedicineDataException("Đơn giá thuốc không được âm.");
        }
        if (stockQuantity < 0) {
            throw new InvalidMedicineDataException("Số lượng tồn kho không được âm.");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
}
