package com.clinicmanager.infrastructure.persistence.examination;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "don_thuoc")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionEntity {

    @Id
    @Column(name = "ma_don_thuoc", length = 36)
    private String id;

    @Column(name = "ngay_ke", nullable = false)
    private LocalDate prescriptionDate;

    @Column(name = "ma_phieu_kham", nullable = false, unique = true, length = 36)
    private String medicalSlipId;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescriptionItemEntity> items;
}
