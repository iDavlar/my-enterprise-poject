package by.davlar.mapper;

import by.davlar.dto.RoleDto;
import by.davlar.hibernate.entity.Role;
import by.davlar.service.RoleService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleService roleService = RoleService.getInstance();

    @Mapping(target = "users", ignore = true)
    Role RoleDtoToRole(RoleDto dto);

    RoleDto RoleToDto(Role role);

    default Role NameToRole(String name) {
        var roleDto = roleService.findByName(name);
        if (roleDto.isEmpty()) {
            roleDto = roleService.getDefault();
        }
        return roleDto.map(INSTANCE::RoleDtoToRole).get();
    }
}
