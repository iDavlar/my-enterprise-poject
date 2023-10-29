package by.davlar.service;

import by.davlar.dto.CreateUserDto;
import by.davlar.dto.UserDto;
import by.davlar.exceptions.ValidationException;
import by.davlar.jdbc.dao.UserDao;
import by.davlar.jdbc.entity.User;
import by.davlar.mapper.CreateUserDtoToUserMapper;
import by.davlar.validator.CreateUserDtoValidator;

import java.util.List;
import java.util.stream.Collectors;


public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final CreateUserDtoToUserMapper createUserDtoToUserMapper = CreateUserDtoToUserMapper.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserDtoValidator createUserDtoValidator = CreateUserDtoValidator.getInstance();

    public static UserService getInstance() {
        return INSTANCE;
    }

    private UserService() {
    }

    public List<UserDto> findAll() {
        return userDao.findAll().stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    public Integer create(CreateUserDto createUserDto) {
        var validationResult = createUserDtoValidator.isValid(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        User user = createUserDtoToUserMapper.mapFrom(createUserDto);
        userDao.save(user);
        return user.getId();
    }
}
