package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.Pizza;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PizzaRepository extends BaseRepository<Integer, Pizza> {
    private static final PizzaRepository INSTANCE = new PizzaRepository();

    public static PizzaRepository getInstance() {
        return INSTANCE;
    }
}
