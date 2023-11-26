package by.davlar.mapper;

import by.davlar.dto.RoleDto;
import by.davlar.hibernate.entity.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleDtoToRoleMapper implements Mapper<Role, RoleDto> {
    private static final RoleDtoToRoleMapper INSTANCE = new RoleDtoToRoleMapper();

    public static RoleDtoToRoleMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Role mapFrom(RoleDto roleDto) {
        return Role.builder()
                .id(roleDto.getId())
                .name(roleDto.getName())
                .isAdmin(roleDto.getIsAdmin())
                .build();
    }
}
