package by.davlar.mapper;

import by.davlar.dto.CreateUserDto;
import by.davlar.dto.RoleDto;
import by.davlar.dto.UserDto;
import by.davlar.hibernate.entity.User;
import by.davlar.service.RoleService;
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
    User CreateUserDtoToUserMapper(CreateUserDto dto);

    @Mapping(target = "birthday", expression = "java( user.getBirthday().toLocalDate() )")
    @Mapping(target = "roleId", expression = "java( user.getRole().getId() )")
    UserDto UserToDtoMapper(User user);

}
