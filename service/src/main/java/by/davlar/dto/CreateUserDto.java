package by.davlar.dto;

import lombok.Builder;
import lombok.Value;

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
