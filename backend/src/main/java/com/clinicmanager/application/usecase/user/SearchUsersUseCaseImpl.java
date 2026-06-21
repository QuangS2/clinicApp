package com.clinicmanager.application.usecase.user;

import com.clinicmanager.application.dto.user.UserDto;
import com.clinicmanager.application.mapper.user.UserMapper;
import com.clinicmanager.application.port.input.user.SearchUsersUseCase;
import com.clinicmanager.application.port.output.user.UserRepositoryPort;
import com.clinicmanager.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchUsersUseCaseImpl implements SearchUsersUseCase {

    private final UserRepositoryPort repositoryPort;
    private final UserMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> search(String username) {
        String searchName = username != null ? username.trim() : "";
        List<User> users = repositoryPort.search(searchName);

        return users.stream()
                .limit(100)
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
