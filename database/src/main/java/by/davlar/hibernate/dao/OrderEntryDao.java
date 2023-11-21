package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.Address;
import by.davlar.hibernate.entity.OrderEntry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderEntryDao extends AbstractDao<Integer, OrderEntry> {
    private static final OrderEntryDao INSTANCE = new OrderEntryDao();

    public static OrderEntryDao getInstance() {
        return INSTANCE;
    }
}
