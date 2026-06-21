package com.clinicmanager.application.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    private String username;
    private String password; // null nếu không cập nhật
    private String role;
    private String status;
}
