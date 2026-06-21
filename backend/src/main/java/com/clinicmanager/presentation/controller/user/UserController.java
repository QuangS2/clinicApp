package com.clinicmanager.presentation.controller.user;

import com.clinicmanager.application.dto.user.CreateUserRequest;
import com.clinicmanager.application.dto.user.UpdateUserRequest;
import com.clinicmanager.application.dto.user.UserDto;
import com.clinicmanager.application.port.input.user.CreateUserUseCase;
import com.clinicmanager.application.port.input.user.UpdateUserUseCase;
import com.clinicmanager.application.port.input.user.GetUserUseCase;
import com.clinicmanager.application.port.input.user.DeleteUserUseCase;
import com.clinicmanager.application.port.input.user.SearchUsersUseCase;
import com.clinicmanager.presentation.response.ApiResponse;
import com.clinicmanager.presentation.response.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUseCase;
    private final UpdateUserUseCase updateUseCase;
    private final GetUserUseCase getUseCase;
    private final DeleteUserUseCase deleteUseCase;
    private final SearchUsersUseCase searchUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> createUser(@Valid @RequestBody com.clinicmanager.presentation.request.user.CreateUserRequest request) {
        CreateUserRequest appRequest = CreateUserRequest.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .role(request.getRole())
                .status(request.getStatus())
                .build();

        UserDto dto = createUseCase.create(appRequest);
        return ApiResponse.success(mapToResponse(dto), "Tạo tài khoản người dùng thành công.");
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody com.clinicmanager.presentation.request.user.UpdateUserRequest request) {

        UpdateUserRequest appRequest = UpdateUserRequest.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .role(request.getRole())
                .status(request.getStatus())
                .build();

        UserDto dto = updateUseCase.update(id, appRequest);
        return ApiResponse.success(mapToResponse(dto), "Cập nhật thông tin tài khoản thành công.");
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable UUID id) {
        UserDto dto = getUseCase.getById(id);
        return ApiResponse.success(mapToResponse(dto), "Lấy thông tin tài khoản thành công.");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable UUID id) {
        deleteUseCase.delete(id);
        return ApiResponse.success(null, "Xóa tài khoản thành công.");
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> searchUsers(@RequestParam(required = false) String username) {
        List<UserDto> dtos = searchUseCase.search(username);
        List<UserResponse> responses = dtos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(responses, "Tra cứu danh sách tài khoản thành công.");
    }

    private UserResponse mapToResponse(UserDto dto) {
        return UserResponse.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .role(dto.getRole())
                .status(dto.getStatus())
                .build();
    }
}
