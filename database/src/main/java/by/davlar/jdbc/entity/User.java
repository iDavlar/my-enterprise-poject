package by.davlar.jdbc.entity;

import by.davlar.jdbc.dao.RoleDao;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String login;
    private String password;
    private String telephone;
    private Integer roleId;
    private Role role;
}
