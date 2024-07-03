package by.davlar.spring.database.repository;

import by.davlar.spring.database.entity.OrderEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEntryRepository extends JpaRepository<OrderEntry, Integer> {
}
