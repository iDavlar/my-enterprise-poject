package by.davlar.spring.mapper;

import by.davlar.spring.database.entity.Role;
import by.davlar.spring.dto.RoleDto;
import by.davlar.spring.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
@RequiredArgsConstructor
public abstract class RoleMapper {

    @Autowired
    protected RoleService roleService;
    public static RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "users", ignore = true)
    public abstract Role mapToRole(RoleDto dto);

    public abstract RoleDto mapToRoleDto(Role role);

    public Role mapNameToRole(String name) {
        var roleDto = roleService.findByName(name);
        if (roleDto.isEmpty()) {
            roleDto = roleService.getDefault();
        }
        return roleDto.map(this::mapToRole).get();
    }
}
