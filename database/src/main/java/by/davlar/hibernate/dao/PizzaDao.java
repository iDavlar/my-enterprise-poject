package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.Address;
import by.davlar.hibernate.entity.Pizza;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PizzaDao extends AbstractDao<Integer, Pizza> {
    private static final PizzaDao INSTANCE = new PizzaDao();

    public static PizzaDao getInstance() {
        return INSTANCE;
    }
}
