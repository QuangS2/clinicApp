package com.clinicmanager.infrastructure.persistence.patient;

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
@Table(name = "benh_nhan")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientEntity {

    @Id
    @Column(name = "ma_benh_nhan", length = 36)
    private String id;

    @Column(name = "ho_ten", nullable = false)
    private String fullName;

    @Column(name = "ngay_sinh", nullable = false)
    private LocalDate dob;

    @Column(name = "gioi_tinh", nullable = false, length = 10)
    private String gender;

    @Column(name = "so_dien_thoai", nullable = false, unique = true, length = 15)
    private String phone;

    @Column(name = "dia_chi")
    private String address;

    @Column(name = "email", unique = true)
    private String email;
}
