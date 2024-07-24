package by.davlar.spring.service;

import by.davlar.spring.database.entity.QUser;
import by.davlar.spring.database.entity.User;
import by.davlar.spring.database.repository.UserRepository;
import by.davlar.spring.dto.UserCreateEditDto;
import by.davlar.spring.dto.UserReadDto;
import by.davlar.spring.dto.filter.UserFilter;
import by.davlar.spring.dto.predicate.QPredicates;
import by.davlar.spring.mapper.UserMapper;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.davlar.spring.database.entity.QUser.user;

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

    public List<UserReadDto> findAll(UserFilter filter, Sort sort) {
        log.info("findAll(filter = {})", filter);

        var predicate = QPredicates.builder()
                .add(filter.getLogin(), user.login::containsIgnoreCase)
                .add(filter.getFirstName(), user.firstName::containsIgnoreCase)
                .add(filter.getBirthday(), (date) -> user.birthday.before(Date.valueOf(date)))
                .build();
        return Lists.newArrayList(userRepository.findAll(predicate, sort)).stream()
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
