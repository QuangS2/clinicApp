package com.clinicmanager.domain.model.service;

import com.clinicmanager.domain.exception.service.InvalidMedicalServiceDataException;
import java.math.BigDecimal;
import java.util.UUID;

public class MedicalService {
    private final UUID id;
    private final String name;
    private final BigDecimal price;
    private final String description;

    public MedicalService(UUID id, String name, BigDecimal price, String description) {
        this.id = id != null ? id : UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.description = description;
        validate();
    }

    private void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidMedicalServiceDataException("Tên dịch vụ không được để trống.");
        }
        if (price == null) {
            throw new InvalidMedicalServiceDataException("Đơn giá dịch vụ không được để trống.");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidMedicalServiceDataException("Đơn giá dịch vụ không được âm.");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
