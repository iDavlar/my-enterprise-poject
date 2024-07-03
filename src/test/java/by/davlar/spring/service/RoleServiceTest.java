package by.davlar.spring.service;

import by.davlar.spring.annotation.IT;
import by.davlar.spring.dto.RoleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Test
    void findAll_findAll_found2RolesAndNoThrow() {
        List<RoleDto> roles = roleService.findAll();

        assertThat(roles).hasSize(2);
    }

    @Test
    void findByName_findUserRole_isPresentAndIsNotAdminAndNoThrow() {
        String name = "USER";
        Optional<RoleDto> user = roleService.findByName(name);

        assertTrue(user.isPresent());
        assertFalse(user.orElseThrow().getIsAdmin());
    }

    @Test
    void getDefault_isPresentAndNoThrow() {
        Optional<RoleDto> role = roleService.getDefault();

        assertTrue(role.isPresent());
    }
}
