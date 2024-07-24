package by.davlar.spring.dto.filter;

import lombok.Value;

import java.time.LocalDate;

@Value
public class UserFilter {
    String login;
    String firstName;
    LocalDate birthday;
}
