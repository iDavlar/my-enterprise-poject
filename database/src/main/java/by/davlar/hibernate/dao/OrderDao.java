package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.Address;
import by.davlar.hibernate.entity.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDao extends AbstractDao<Integer, Order> {
    private static final OrderDao INSTANCE = new OrderDao();

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
