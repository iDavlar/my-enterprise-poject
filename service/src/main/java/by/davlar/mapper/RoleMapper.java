package by.davlar.mapper;

import by.davlar.dto.RoleDto;
import by.davlar.hibernate.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "users", ignore = true)
    Role RoleDtoToRoleMapper(RoleDto dto);

    RoleDto RoleToDtoMapper(Role role);
}
