package com.clinicmanager.infrastructure.persistence.examination;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "don_thuoc_chi_tiet")
@IdClass(PrescriptionItemId.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItemEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "ma_don_thuoc", nullable = false)
    private PrescriptionEntity prescription;

    @Id
    @Column(name = "ma_thuoc", nullable = false, length = 36)
    private String medicineId;

    @Column(name = "so_luong", nullable = false)
    private Integer quantity;

    @Column(name = "lieu_dung", nullable = false, length = 500)
    private String instruction;
}
