package by.davlar.service;

import by.davlar.dto.CreateUserDto;
import by.davlar.dto.UserDto;
import by.davlar.exceptions.ValidationException;
import by.davlar.hibernate.dao.UserDao;
import by.davlar.mapper.CreateUserDtoToUserMapper;
import by.davlar.mapper.UserToDtoMapper;
import by.davlar.validator.CreateUserDtoValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final CreateUserDtoToUserMapper createUserDtoToUserMapper = CreateUserDtoToUserMapper.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserDtoValidator createUserDtoValidator = CreateUserDtoValidator.getInstance();
    private final UserToDtoMapper userToDtoMapper = UserToDtoMapper.getInstance();

    public static UserService getInstance() {
        return INSTANCE;
    }

    private UserService() {
    }

    public List<UserDto> findAll() {
        return userDao.findAll().stream()
                .map(userToDtoMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public Integer create(CreateUserDto createUserDto) {
        var validationResult = createUserDtoValidator.isValid(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        var user = userDao.save(
                Optional.of(createUserDto).map(createUserDtoToUserMapper::mapFrom).orElseThrow()
        );
        return user.getId();
    }

    public Optional<UserDto> login(String login, String password) {
        return userDao.findByLoginPassword(login, password)
                .map(userToDtoMapper::mapFrom);
    }
}
