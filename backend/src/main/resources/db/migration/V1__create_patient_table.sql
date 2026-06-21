CREATE TABLE benh_nhan (
    ma_benh_nhan VARCHAR(36) NOT NULL PRIMARY KEY,
    ho_ten VARCHAR(255) NOT NULL,
    ngay_sinh DATE NOT NULL,
    gioi_tinh VARCHAR(10) NOT NULL,
    so_dien_thoai VARCHAR(15) NOT NULL UNIQUE,
    dia_chi VARCHAR(255),
    email VARCHAR(255) UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
