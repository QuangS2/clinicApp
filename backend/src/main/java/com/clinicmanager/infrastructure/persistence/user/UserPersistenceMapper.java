package com.clinicmanager.infrastructure.persistence.user;

import com.clinicmanager.domain.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

    @Mapping(target = "id", expression = "java(domain.getId() != null ? domain.getId().toString() : null)")
    UserEntity toEntity(User domain);

    default User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        UUID uuid = entity.getId() != null ? UUID.fromString(entity.getId()) : null;
        return new User(
                uuid,
                entity.getUsername(),
                entity.getPassword(),
                entity.getRole(),
                entity.getStatus()
        );
    }
}
