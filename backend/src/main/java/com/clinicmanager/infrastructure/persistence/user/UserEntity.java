package com.clinicmanager.infrastructure.persistence.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tai_khoan")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "ma_tai_khoan", length = 36)
    private String id;

    @Column(name = "ten_dang_nhap", nullable = false, unique = true)
    private String username;

    @Column(name = "mat_khau", nullable = false)
    private String password;

    @Column(name = "vai_tro", nullable = false, length = 50)
    private String role;

    @Column(name = "trang_thai", nullable = false, length = 50)
    private String status;
}
