CREATE TABLE don_thuoc (
    ma_don_thuoc VARCHAR(36) NOT NULL PRIMARY KEY,
    ngay_ke DATE NOT NULL,
    ma_phieu_kham VARCHAR(36) NOT NULL UNIQUE,
    CONSTRAINT fk_don_thuoc_phieu_kham FOREIGN KEY (ma_phieu_kham) REFERENCES phieu_kham(ma_phieu_kham)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE don_thuoc_chi_tiet (
    ma_don_thuoc VARCHAR(36) NOT NULL,
    ma_thuoc VARCHAR(36) NOT NULL,
    so_luong INT NOT NULL,
    lieu_dung VARCHAR(500) NOT NULL,
    PRIMARY KEY (ma_don_thuoc, ma_thuoc),
    CONSTRAINT fk_dtct_don_thuoc FOREIGN KEY (ma_don_thuoc) REFERENCES don_thuoc(ma_don_thuoc) ON DELETE CASCADE,
    CONSTRAINT fk_dtct_thuoc FOREIGN KEY (ma_thuoc) REFERENCES thuoc(ma_thuoc)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
