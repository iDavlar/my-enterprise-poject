package by.davlar.spring.mapper;

import by.davlar.spring.database.entity.Role;
import by.davlar.spring.dto.RoleDto;
import by.davlar.spring.service.RoleService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        componentModel = "spring"
)
public abstract class RoleMapper {

    @Autowired
    protected RoleService roleService;
    public static RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "users", ignore = true)
    public abstract Role RoleDtoToRole(RoleDto dto);

    public abstract RoleDto RoleToDto(Role role);

    Role NameToRole(String name) {
        var roleDto = roleService.findByName(name);
        if (roleDto.isEmpty()) {
            roleDto = roleService.getDefault();
        }
        return roleDto.map(INSTANCE::RoleDtoToRole).get();
    }
}
