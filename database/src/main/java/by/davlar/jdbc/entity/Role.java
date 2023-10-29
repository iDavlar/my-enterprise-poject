package by.davlar.jdbc.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role {
    private Integer id;
    private String name;
    private Boolean isAdmin;
}
