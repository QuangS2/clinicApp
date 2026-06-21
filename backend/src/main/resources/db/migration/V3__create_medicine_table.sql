CREATE TABLE thuoc (
    ma_thuoc VARCHAR(36) NOT NULL PRIMARY KEY,
    ten_thuoc VARCHAR(255) NOT NULL UNIQUE,
    don_vi_tinh VARCHAR(50) NOT NULL,
    don_gia DECIMAL(15,2) NOT NULL,
    so_luong_ton INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
