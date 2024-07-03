package by.davlar.spring.service;

import by.davlar.spring.database.repository.UserRepository;
import by.davlar.spring.dto.CreateUserDto;
import by.davlar.spring.dto.UserDto;
import by.davlar.spring.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleService roleService;

    public List<UserDto> findAll() {
        log.info("findAll()");
        return userRepository.findAll().stream()
                .map(userMapper::UserToDtoMapper)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> create(@Valid CreateUserDto createUserDto) {
        log.info("create(createUserDto = {})", createUserDto);

//        @Cleanup var validatorFactory = Validation.buildDefaultValidatorFactory();
//        var validator = validatorFactory.getValidator();
//        var validationResult = validator.validate(createUserDto);
//        if (!validationResult.isEmpty()) {
//            throw new ConstraintViolationException(validationResult);
//        }

        var user = userRepository.save(
                Optional.of(createUserDto)
                        .map(userMapper::CreateUserDtoToUserMapper)
                        .orElseThrow()
        );

        return Optional.of(user)
                .map(userMapper::UserToDtoMapper);
    }

    public Optional<UserDto> login(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password)
                .map(userMapper::UserToDtoMapper);
    }
}
