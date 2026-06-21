CREATE TABLE ho_so_benh_an (
    ma_benh_an VARCHAR(36) NOT NULL PRIMARY KEY,
    chan_doan VARCHAR(1000) NOT NULL,
    ngay_lap DATE NOT NULL,
    ghi_chu VARCHAR(1000),
    ma_phieu_kham VARCHAR(36) NOT NULL UNIQUE,
    CONSTRAINT fk_hsba_phieu_kham FOREIGN KEY (ma_phieu_kham) REFERENCES phieu_kham(ma_phieu_kham)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
