package by.davlar.mapper;

import by.davlar.dto.RoleDto;
import by.davlar.jdbc.entity.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleToDtoMapper implements Mapper<RoleDto, Role> {

    private static final RoleToDtoMapper INSTANCE = new RoleToDtoMapper();

    public static RoleToDtoMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public RoleDto mapFrom(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .isAdmin(role.getIsAdmin())
                .build();
    }
}
