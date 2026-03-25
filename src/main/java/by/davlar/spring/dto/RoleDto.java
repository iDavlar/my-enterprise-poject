package by.davlar.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class RoleDto implements GrantedAuthority {
    private Integer id;
    private String name;
    private Boolean isAdmin;

    @Override
    public String getAuthority() {
        return this.getName();
    }
}
