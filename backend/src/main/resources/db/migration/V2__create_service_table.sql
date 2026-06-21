CREATE TABLE dich_vu (
    ma_dich_vu VARCHAR(36) NOT NULL PRIMARY KEY,
    ten_dich_vu VARCHAR(255) NOT NULL UNIQUE,
    don_gia DECIMAL(15,2) NOT NULL,
    mo_ta VARCHAR(1000)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
