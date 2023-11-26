package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.OrderEntry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderEntryDaoTest {

    private static final OrderEntryDao orderEntryDao = OrderEntryDao.getInstance();
    private static final OrderDao orderDao = OrderDao.getInstance();
    private static final PizzaDao pizzaDao = PizzaDao.getInstance();

    @Test
    public void OrderEntryDaoCRUD_NoFail() {
        try  {
            OrderEntry entity = OrderEntry.builder()
                    .order(orderDao.findById(1).orElseThrow())
                    .pizza(pizzaDao.findById(2).orElseThrow())
                    .amount(15)
                    .build();

            orderEntryDao.save(entity);

            var foundEntity = orderEntryDao.findById(entity.getId()).orElseThrow();
            assertEquals(entity, foundEntity);

            foundEntity.setAmount(20);
            orderEntryDao.update(foundEntity);

            assertTrue(orderEntryDao.delete(foundEntity));

        } catch (NullPointerException e) {
            fail("Something went wrong");
        } catch (Exception e) {
            fail("Connection error: " + e.getMessage());
        }
    }

}