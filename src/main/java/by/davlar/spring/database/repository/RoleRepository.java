package by.davlar.spring.database.repository;

import by.davlar.spring.database.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Integer DEFAULT_ID = 2;

    default Integer getDefaultId() {
        return DEFAULT_ID;
    }

    Optional<Role> findByName(String name);
}
