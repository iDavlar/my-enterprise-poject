package by.davlar.mapper;

import by.davlar.dto.CreateUserDto;
import by.davlar.dto.RoleDto;
import by.davlar.dto.UserDto;
import by.davlar.hibernate.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

//@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User CreateUserDtoToUserMapper(CreateUserDto dto);

    UserDto UserToDtoMapper(User user);

}
