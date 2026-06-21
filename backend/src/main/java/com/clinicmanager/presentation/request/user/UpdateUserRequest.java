package com.clinicmanager.presentation.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @NotBlank(message = "Tên đăng nhập không được để trống.")
    private String username;

    private String password; // null hoặc trống nếu không đổi

    @NotBlank(message = "Vai trò không được để trống.")
    private String role;

    @NotBlank(message = "Trạng thái không được để trống.")
    private String status;
}
