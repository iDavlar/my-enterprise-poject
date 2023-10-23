package by.davlar.service;

import by.davlar.dto.UserDto;
import by.davlar.jdbc.dao.UserDao;
import by.davlar.jdbc.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

//@ExtendWith(MockitoExtension.class)
class UserServiceTest {
//    @Mock
//    private UserDao userDao;
//    @InjectMocks
//    private UserService userService;

    @Test
    void findAll_NotEmpty_True() {
//        try (var userDaoMock = Mockito.mockStatic(UserDao.class);
//             var userServiceMock = Mockito.mockStatic(UserService.class)) {
//            userDaoMock.when(UserDao::getInstance).thenReturn(userDao);
//            userServiceMock.when(UserService.)
//            Mockito.when(userDao.findAll()).thenReturn(getTestUsers());
//
//            List<UserDto> users = userService.findAll();
//            Assertions.assertFalse(users.isEmpty());
//        }
    }

    private List<User> getTestUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.add(User.builder()
                .id(1)
                .build());
        return users;
    }
}
