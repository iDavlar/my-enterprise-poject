package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoTest {

    private static final OrderDao orderDao = OrderDao.getInstance();
    private static final UserDao userDao = UserDao.getInstance();
    private static final AddressDao addressDao = AddressDao.getInstance();

    @Test
    public void OrderDaoCRUD_NoFail() {
        try {
            Order entity = Order.builder()
                    .user(userDao.findById(1).orElseThrow())
                    .address(addressDao.findById(1).orElseThrow())
                    .date(LocalDateTime.now().withNano(0))
                    .build();

            orderDao.save(entity);

            var foundEntity = orderDao.findById(entity.getId()).orElseThrow();
            assertEquals(entity, foundEntity);

            foundEntity.setDate(
                    foundEntity.getDate().plusHours(15)
            );
            orderDao.update(foundEntity);

            assertTrue(orderDao.delete(foundEntity));

        } catch (NullPointerException e) {
            fail("Something went wrong");
        } catch (Exception e) {
            fail("Connection error: " + e.getMessage());
        }
    }

}