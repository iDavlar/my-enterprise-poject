package by.davlar.spring.mapper;

import by.davlar.spring.dto.CreateUserDto;
import by.davlar.spring.dto.UserDto;
import by.davlar.spring.database.entity.User;
import by.davlar.spring.service.RoleService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.sql.Date;

@Mapper(
        uses = {
                RoleMapper.class
        },
        imports = {
                Date.class
        }
)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "birthday", expression = "java( Date.valueOf(dto.getBirthday()) )")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    User CreateUserDtoToUserMapper(CreateUserDto dto, @Context RoleService roleService);

    @Mapping(target = "birthday", expression = "java( user.getBirthday().toLocalDate() )")
    @Mapping(target = "roleId", expression = "java( user.getRole().getId() )")
    UserDto UserToDtoMapper(User user);

}
