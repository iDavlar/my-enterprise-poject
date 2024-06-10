package by.davlar.spring.database.repository;

import by.davlar.spring.database.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
