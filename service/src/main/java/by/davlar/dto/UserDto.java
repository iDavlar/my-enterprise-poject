package by.davlar.dto;

import by.davlar.jdbc.entity.User;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
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

    public User toEntity() {
        return User.builder()
                .id(this.getId())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .birthday(Date.valueOf(this.getBirthday()))
                .login(this.getLogin())
                .password(this.getPassword())
                .telephone(this.getTelephone())
                .roleId(this.getRoleId())
                .build();
    }
}
