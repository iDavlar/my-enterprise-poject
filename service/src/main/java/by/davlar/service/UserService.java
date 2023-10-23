package by.davlar.service;

import by.davlar.dto.UserDto;
import by.davlar.jdbc.dao.UserDao;

import java.util.List;
import java.util.stream.Collectors;


public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();

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
}
