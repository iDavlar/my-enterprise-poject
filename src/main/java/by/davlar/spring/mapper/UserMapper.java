package by.davlar.spring.mapper;

import by.davlar.spring.database.entity.User;
import by.davlar.spring.dto.UserCreateEditDto;
import by.davlar.spring.dto.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        uses = {
                RoleMapper.class
        },
        imports = {
                Date.class
        }
)
public abstract class UserMapper {
    private final RoleMapper roleMapper = RoleMapper.INSTANCE;
//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "birthday", expression = "java( Date.valueOf(dto.getBirthday()) )")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    public abstract User mapToUser(UserCreateEditDto dto);

    @Mapping(target = "birthday", expression = "java( Date.valueOf(dto.getBirthday()) )")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    public abstract User mapToUser(UserReadDto dto);

    @Mapping(target = "birthday", expression = "java( user.getBirthday().toLocalDate() )")
    @Mapping(target = "roleId", expression = "java( user.getRole().getId() )")
    public abstract UserReadDto mapToUserReadDto(User user);

    public User mapToUser(UserCreateEditDto dto, User user) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setBirthday(Date.valueOf(dto.getBirthday()));
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        user.setTelephone(dto.getTelephone());
        user.setRole(roleMapper.NameToRole(dto.getRole()));
        return user;
    }


}
