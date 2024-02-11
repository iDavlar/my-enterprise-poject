package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.OrderEntry;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderEntryRepository extends BaseRepository<Integer, OrderEntry> {
    private static final OrderEntryRepository INSTANCE = new OrderEntryRepository();

    public static OrderEntryRepository getInstance() {
        return INSTANCE;
    }
}
