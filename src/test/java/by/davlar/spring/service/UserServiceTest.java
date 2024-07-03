package by.davlar.spring.service;

import by.davlar.spring.annotation.IT;
import by.davlar.spring.dto.CreateUserDto;
import by.davlar.spring.dto.UserDto;
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
        List<UserDto> users = userService.findAll();

        assertFalse(users.isEmpty());
    }

    @Test
    void create_newUser_isPresentAndNoThrow() {
        CreateUserDto userDto = CreateUserDto.builder()
                .firstName("Test123")
                .lastName("Test123")
                .birthday("1999-04-28")
                .login("Test123456")
                .password("123456")
                .role("USER")
                .build();

        Optional<UserDto> user = userService.create(userDto);

        assertTrue(user.isPresent());
    }
    @Test
    void login_isPresent() {
        String login = "Davlar";
        String password = "123456";
        Optional<UserDto> user = userService.login(login, password);

        assertTrue(user.isPresent());
    }

}
