package by.davlar.dto;

import by.davlar.jdbc.entity.Role;
import by.davlar.jdbc.entity.User;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Builder
public class RoleDto {
    private Integer id;
    private String name;
    private Boolean isAdmin;

    public static RoleDto from(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .isAdmin(role.getIsAdmin())
                .build();
    }

    public Role toEntity() {
        return Role.builder()
                .id(this.getId())
                .name(this.getName())
                .isAdmin(this.getIsAdmin())
                .build();
    }
}
