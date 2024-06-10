package by.davlar.spring.service;

import by.davlar.spring.database.repository.UserRepository;
import by.davlar.spring.dto.CreateUserDto;
import by.davlar.spring.dto.UserDto;
import by.davlar.spring.mapper.UserMapper;
import by.davlar.spring.validator.CreateUserDtoValidator;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.davlar.spring.database.utils.EntityGraphHelper.WITH_ROLE;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final CreateUserDtoValidator createUserDtoValidator = CreateUserDtoValidator.getInstance();

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::UserToDtoMapper)
                .collect(Collectors.toList());
    }

    public Integer create(CreateUserDto createUserDto) {
        @Cleanup var validatorFactory = Validation.buildDefaultValidatorFactory();
        var validator = validatorFactory.getValidator();
        var validationResult = validator.validate(createUserDto);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }
//        var validationResult = createUserDtoValidator.isValid(createUserDto);
//        if (!validationResult.isValid()) {
//            throw new ValidationException(validationResult.getErrors());
//        }

        var user = userRepository.save(
                Optional.of(createUserDto)
                        .map(dto -> userMapper.CreateUserDtoToUserMapper(dto, roleService))
                        .orElseThrow()
        );
        return user.getId();
    }

    public Optional<UserDto> login(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password)
                .map(userMapper::UserToDtoMapper);
    }
}
