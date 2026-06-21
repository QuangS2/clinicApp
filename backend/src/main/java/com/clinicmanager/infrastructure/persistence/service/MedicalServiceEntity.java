package com.clinicmanager.infrastructure.persistence.service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "dich_vu")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalServiceEntity {

    @Id
    @Column(name = "ma_dich_vu", length = 36)
    private String id;

    @Column(name = "ten_dich_vu", nullable = false, unique = true)
    private String name;

    @Column(name = "don_gia", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "mo_ta", length = 1000)
    private String description;
}
