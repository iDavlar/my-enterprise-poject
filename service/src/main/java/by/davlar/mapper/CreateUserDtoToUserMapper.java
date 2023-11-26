package by.davlar.mapper;

import by.davlar.dto.CreateUserDto;
import by.davlar.hibernate.entity.User;
import by.davlar.service.RoleService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserDtoToUserMapper implements Mapper<User, CreateUserDto> {
    private static final CreateUserDtoToUserMapper INSTANCE = new CreateUserDtoToUserMapper();
    private final RoleDtoToRoleMapper roleDtoToRoleMapper = RoleDtoToRoleMapper.getInstance();
    private final RoleService roleService = RoleService.getInstance();

    public static CreateUserDtoToUserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public User mapFrom(CreateUserDto createUserDto) {
        var role = roleService.findByName(createUserDto.getRole());
        if (role.isEmpty()) {
            role = roleService.getDefault();
        }

        return User.builder()
                .firstName(createUserDto.getFirstName())
                .lastName(createUserDto.getLastName())
                .birthday(Date.valueOf(createUserDto.getBirthday()))
                .login(createUserDto.getLogin())
                .password(createUserDto.getPassword())
                .telephone(createUserDto.getTelephone())
                .role(role.map(roleDtoToRoleMapper::mapFrom).get())
                .build();
    }
}
