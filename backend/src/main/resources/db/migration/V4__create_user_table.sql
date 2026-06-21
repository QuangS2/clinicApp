CREATE TABLE tai_khoan (
    ma_tai_khoan VARCHAR(36) NOT NULL PRIMARY KEY,
    ten_dang_nhap VARCHAR(255) NOT NULL UNIQUE,
    mat_khau VARCHAR(255) NOT NULL,
    vai_tro VARCHAR(50) NOT NULL,
    trang_thai VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
