package by.davlar.spring.mapper;

import by.davlar.spring.database.entity.User;
import by.davlar.spring.dto.CreateUserDto;
import by.davlar.spring.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Date;

@Mapper(
        componentModel = "spring",
        uses = {
                RoleMapper.class
        },
        imports = {
                Date.class
        }
)
public abstract class UserMapper {
//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "birthday", expression = "java( Date.valueOf(dto.getBirthday()) )")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    public abstract User CreateUserDtoToUserMapper(CreateUserDto dto);

    @Mapping(target = "birthday", expression = "java( user.getBirthday().toLocalDate() )")
    @Mapping(target = "roleId", expression = "java( user.getRole().getId() )")
    public abstract UserDto UserToDtoMapper(User user);

}
