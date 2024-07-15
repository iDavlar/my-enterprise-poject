package by.davlar.spring.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Data
@Builder
@FieldNameConstants
public class UserReadDto {
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
