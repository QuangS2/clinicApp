package com.clinicmanager.infrastructure.persistence.examination;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "ho_so_benh_an")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordEntity {

    @Id
    @Column(name = "ma_benh_an", length = 36)
    private String id;

    @Column(name = "chan_doan", nullable = false, length = 1000)
    private String diagnosis;

    @Column(name = "ngay_lap", nullable = false)
    private LocalDate createdDate;

    @Column(name = "ghi_chu", length = 1000)
    private String notes;

    @Column(name = "ma_phieu_kham", nullable = false, unique = true, length = 36)
    private String medicalSlipId;
}
