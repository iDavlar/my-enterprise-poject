package by.davlar.spring.service;

import by.davlar.spring.annotation.IT;
import by.davlar.spring.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@IT
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void findAll_NotEmpty_True() {
        List<UserDto> users = userService.findAll();

        assertFalse(users.isEmpty());

        users.forEach(System.out::println);
    }

}
