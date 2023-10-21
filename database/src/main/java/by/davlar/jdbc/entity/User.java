package by.davlar.jdbc.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String login;
    private String password;
    private String telephone;
}
