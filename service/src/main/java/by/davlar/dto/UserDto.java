package by.davlar.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String login;
    private String password;
    private String telephone;
    private Integer roleId;
    private RoleDto role;
}
