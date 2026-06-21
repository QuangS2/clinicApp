package com.clinicmanager.application.usecase.user;

import com.clinicmanager.application.dto.user.CreateUserRequest;
import com.clinicmanager.application.dto.user.UpdateUserRequest;
import com.clinicmanager.application.dto.user.UserDto;
import com.clinicmanager.application.mapper.user.UserMapper;
import com.clinicmanager.application.port.output.user.UserRepositoryPort;
import com.clinicmanager.domain.exception.user.InvalidUserDataException;
import com.clinicmanager.domain.exception.user.UserAlreadyExistsException;
import com.clinicmanager.domain.exception.user.UserNotFoundException;
import com.clinicmanager.domain.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserRepositoryPort repositoryPort;

    @Mock
    private UserMapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private CreateUserUseCaseImpl createUseCase;
    private UpdateUserUseCaseImpl updateUseCase;
    private GetUserUseCaseImpl getUseCase;
    private DeleteUserUseCaseImpl deleteUseCase;
    private SearchUsersUseCaseImpl searchUseCase;

    private User user1;
    private UserDto userDto1;

    @BeforeEach
    void setUp() {
        createUseCase = new CreateUserUseCaseImpl(repositoryPort, mapper, passwordEncoder);
        updateUseCase = new UpdateUserUseCaseImpl(repositoryPort, mapper, passwordEncoder);
        getUseCase = new GetUserUseCaseImpl(repositoryPort, mapper);
        deleteUseCase = new DeleteUserUseCaseImpl(repositoryPort);
        searchUseCase = new SearchUsersUseCaseImpl(repositoryPort, mapper);

        user1 = new User(
                UUID.randomUUID(),
                "admin",
                "$2a$10$encryptedpassword",
                "QUAN_LY",
                "ACTIVE"
        );

        userDto1 = UserDto.builder()
                .id(user1.getId())
                .username("admin")
                .role("QUAN_LY")
                .status("ACTIVE")
                .build();
    }

    @Test
    void create_Success() {
        CreateUserRequest request = CreateUserRequest.builder()
                .username("admin")
                .password("password123")
                .role("QUAN_LY")
                .status("ACTIVE")
                .build();

        when(repositoryPort.existsByUsername("admin")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("$2a$10$encryptedpassword");
        when(repositoryPort.save(any(User.class))).thenReturn(user1);
        when(mapper.toDto(user1)).thenReturn(userDto1);

        UserDto result = createUseCase.create(request);

        assertNotNull(result);
        assertEquals("admin", result.getUsername());
        verify(passwordEncoder, times(1)).encode("password123");
        verify(repositoryPort, times(1)).save(any(User.class));
    }

    @Test
    void create_Failure_AlreadyExists() {
        CreateUserRequest request = CreateUserRequest.builder()
                .username("admin")
                .password("password")
                .role("QUAN_LY")
                .status("ACTIVE")
                .build();

        when(repositoryPort.existsByUsername("admin")).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> createUseCase.create(request));
        verify(repositoryPort, never()).save(any());
    }

    @Test
    void update_Success() {
        UUID id = user1.getId();
        UpdateUserRequest request = UpdateUserRequest.builder()
                .username("admin_new")
                .password("newpassword")
                .role("BAC_SI")
                .status("INACTIVE")
                .build();

        User updatedDomain = new User(
                id,
                "admin_new",
                "$2a$10$newencrypted",
                "BAC_SI",
                "INACTIVE"
        );

        UserDto updatedDto = UserDto.builder()
                .id(id)
                .username("admin_new")
                .role("BAC_SI")
                .status("INACTIVE")
                .build();

        when(repositoryPort.findById(id)).thenReturn(Optional.of(user1));
        when(repositoryPort.existsByUsername("admin_new")).thenReturn(false);
        when(passwordEncoder.encode("newpassword")).thenReturn("$2a$10$newencrypted");
        when(repositoryPort.save(any(User.class))).thenReturn(updatedDomain);
        when(mapper.toDto(any(User.class))).thenReturn(updatedDto);

        UserDto result = updateUseCase.update(id, request);

        assertNotNull(result);
        assertEquals("admin_new", result.getUsername());
        assertEquals("BAC_SI", result.getRole());
        assertEquals("INACTIVE", result.getStatus());
    }

    @Test
    void update_Failure_NotFound() {
        UUID id = UUID.randomUUID();
        UpdateUserRequest request = UpdateUserRequest.builder()
                .username("admin")
                .role("LE_TAN")
                .status("ACTIVE")
                .build();

        when(repositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> updateUseCase.update(id, request));
    }

    @Test
    void getById_Success() {
        UUID id = user1.getId();
        when(repositoryPort.findById(id)).thenReturn(Optional.of(user1));
        when(mapper.toDto(user1)).thenReturn(userDto1);

        UserDto result = getUseCase.getById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void delete_Success() {
        UUID id = user1.getId();
        when(repositoryPort.findById(id)).thenReturn(Optional.of(user1));

        assertDoesNotThrow(() -> deleteUseCase.delete(id));
        verify(repositoryPort, times(1)).delete(id);
    }

    @Test
    void search_Success_LimitsTo100() {
        List<User> mockList = new ArrayList<>();
        for (int i = 0; i < 105; i++) {
            mockList.add(user1);
        }
        when(repositoryPort.search("admin")).thenReturn(mockList);
        when(mapper.toDto(any(User.class))).thenReturn(userDto1);

        List<UserDto> result = searchUseCase.search("admin");

        assertNotNull(result);
        assertEquals(100, result.size());
    }
}
