package com.clinicmanager.domain.model.user;

import com.clinicmanager.domain.exception.user.InvalidUserDataException;
import java.util.Set;
import java.util.UUID;

public class User {
    private final UUID id;
    private final String username;
    private final String password;
    private final String role;
    private final String status;

    private static final Set<String> VALID_ROLES = Set.of("BAC_SI", "LE_TAN", "THU_NGAN", "KTV", "QUAN_LY");
    private static final Set<String> VALID_STATUSES = Set.of("ACTIVE", "INACTIVE");

    public User(UUID id, String username, String password, String role, String status) {
        this.id = id != null ? id : UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        validate();
    }

    private void validate() {
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidUserDataException("Tên đăng nhập không được để trống.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new InvalidUserDataException("Mật khẩu không được để trống.");
        }
        if (role == null || !VALID_ROLES.contains(role.toUpperCase())) {
            throw new InvalidUserDataException("Vai trò không hợp lệ. Phải là một trong: BAC_SI, LE_TAN, THU_NGAN, KTV, QUAN_LY.");
        }
        if (status == null || !VALID_STATUSES.contains(status.toUpperCase())) {
            throw new InvalidUserDataException("Trạng thái không hợp lệ. Phải là: ACTIVE hoặc INACTIVE.");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }
}
