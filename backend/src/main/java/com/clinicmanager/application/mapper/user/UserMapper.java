package com.clinicmanager.application.mapper.user;

import com.clinicmanager.application.dto.user.UserDto;
import com.clinicmanager.application.dto.user.CreateUserRequest;
import com.clinicmanager.domain.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User domain);

    @Mapping(target = "id", ignore = true)
    User toDomain(CreateUserRequest request);
}
