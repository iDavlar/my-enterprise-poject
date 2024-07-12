package by.davlar.spring.service;

import by.davlar.spring.annotation.IT;
import by.davlar.spring.dto.UserCreateEditDto;
import by.davlar.spring.dto.UserReadDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void findAll_NotEmptyAndNoThrow() {
        List<UserReadDto> users = userService.findAll();

        assertFalse(users.isEmpty());
    }

    @Test
    void create_newUser_isPresentAndNoThrow() {
        UserCreateEditDto userDto = UserCreateEditDto.builder()
                .firstName("Test123")
                .lastName("Test123")
                .birthday("1999-04-28")
                .login("Test123456")
                .password("123456")
                .role("USER")
                .build();

        Optional<UserReadDto> user = userService.create(userDto);

        assertTrue(user.isPresent());
    }
    @Test
    void login_isPresent() {
        String login = "Davlar";
        String password = "123456";
        Optional<UserReadDto> user = userService.login(login, password);

        assertTrue(user.isPresent());
    }

}
