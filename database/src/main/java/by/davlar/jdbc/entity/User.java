package by.davlar.jdbc.entity;

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
}
