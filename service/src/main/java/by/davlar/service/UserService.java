package by.davlar.service;

import by.davlar.dto.CreateUserDto;
import by.davlar.dto.UserDto;
import by.davlar.exceptions.ValidationException;
import by.davlar.hibernate.dao.UserRepository;
import by.davlar.hibernate.utils.ConfigurationManager;
import by.davlar.mapper.UserMapper;
import by.davlar.validator.CreateUserDtoValidator;
import lombok.Cleanup;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.davlar.hibernate.utils.FetchProfileHelper.WITH_ROLE;


public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final SessionFactory sessionFactory = ConfigurationManager.getSessionFactory();
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final UserRepository userDao = UserRepository.getInstance();
    private final CreateUserDtoValidator createUserDtoValidator = CreateUserDtoValidator.getInstance();

    public static UserService getInstance() {
        return INSTANCE;
    }

    private UserService() {
    }

    public List<UserDto> findAll() {
        @Cleanup var session = sessionFactory.openSession();
        session.enableFetchProfile(WITH_ROLE);
        return userDao.findAll(session).stream()
                .map(userMapper::UserToDtoMapper)
                .collect(Collectors.toList());
    }

    public Integer create(CreateUserDto createUserDto) {
        var validationResult = createUserDtoValidator.isValid(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        var user = userDao.save(
                Optional.of(createUserDto).map(userMapper::CreateUserDtoToUserMapper).orElseThrow()
        );
        return user.getId();
    }

    public Optional<UserDto> login(String login, String password) {
        return userDao.findByLoginPassword(login, password)
                .map(userMapper::UserToDtoMapper);
    }
}
