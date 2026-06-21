package com.clinicmanager.infrastructure.persistence.medicine;

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
@Table(name = "thuoc")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineEntity {

    @Id
    @Column(name = "ma_thuoc", length = 36)
    private String id;

    @Column(name = "ten_thuoc", nullable = false, unique = true)
    private String name;

    @Column(name = "don_vi_tinh", nullable = false, length = 50)
    private String unit;

    @Column(name = "don_gia", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "so_luong_ton", nullable = false)
    private int stockQuantity;
}
