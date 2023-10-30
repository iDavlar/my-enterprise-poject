package by.davlar.mapper;

import by.davlar.dto.UserDto;
import by.davlar.jdbc.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserToDtoMapper implements Mapper<UserDto, User> {

    private static final UserToDtoMapper INSTANCE = new UserToDtoMapper();

    public static UserToDtoMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public UserDto mapFrom(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthday(user.getBirthday().toLocalDate())
                .login(user.getLogin())
                .password(user.getPassword())
                .telephone(user.getTelephone())
                .roleId(user.getRoleId())
                .build();
    }
}
