package by.davlar.dto;

import by.davlar.jdbc.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.sql.Date;
import java.time.LocalDate;

@Value
@Builder
public class CreateUserDto {
    String firstName;
    String lastName;
    String birthday;
    String login;
    String password;
    String telephone;
    String role;
}
