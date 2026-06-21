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
public class CreateUserRequest {

    @NotBlank(message = "Tên đăng nhập không được để trống.")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống.")
    private String password;

    @NotBlank(message = "Vai trò không được để trống.")
    private String role;

    @NotBlank(message = "Trạng thái không được để trống.")
    private String status;
}
