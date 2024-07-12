package by.davlar.spring.service;

import by.davlar.spring.database.repository.UserRepository;
import by.davlar.spring.dto.UserCreateEditDto;
import by.davlar.spring.dto.UserReadDto;
import by.davlar.spring.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleService roleService;

    public List<UserReadDto> findAll() {
        log.info("findAll()");
        return userRepository.findAll().stream()
                .map(userMapper::mapToUserReadDto)
                .toList();
    }

    public Optional<UserReadDto> findById(Integer id) {
        log.info("findById(id = {})", id);
        return userRepository.findById(id)
                .map(userMapper::mapToUserReadDto);
    }

    @Transactional
    public Optional<UserReadDto> create(@Valid UserCreateEditDto userCreateEditDto) {
        log.info("create(createUserDto = {})", userCreateEditDto);

//        @Cleanup var validatorFactory = Validation.buildDefaultValidatorFactory();
//        var validator = validatorFactory.getValidator();
//        var validationResult = validator.validate(createUserDto);
//        if (!validationResult.isEmpty()) {
//            throw new ConstraintViolationException(validationResult);
//        }

        return Optional.of(userCreateEditDto)
                .map(userMapper::mapToUser)
                .map(userRepository::save)
                .map(userMapper::mapToUserReadDto);
    }

    public Optional<UserReadDto> login(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password)
                .map(userMapper::mapToUserReadDto);
    }

    @Transactional
    public boolean delete(Integer id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }


    @Transactional
    public UserReadDto update(Integer id, UserCreateEditDto userCreateEditDto) {
        return userRepository.findById(id)
                .map(entity -> userMapper.mapToUser(userCreateEditDto, entity))
                .map(userRepository::saveAndFlush)
                .map(userMapper::mapToUserReadDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> save(UserReadDto dto) {
//        if (!UserValidator.validate(dto)) {
//            throw new IllegalArgumentException();
//        }
        return Optional.of(dto)
                .map(userMapper::mapToUser)
                .map(userRepository::save)
                .map(userMapper::mapToUserReadDto);

    }
}
