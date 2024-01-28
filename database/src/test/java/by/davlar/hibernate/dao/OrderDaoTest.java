package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.Order;
import by.davlar.hibernate.utils.ConfigurationManager;
import by.davlar.hibernate.utils.TestDataImporter;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class OrderDaoTest {

    private final SessionFactory sessionFactory = ConfigurationManager.getSessionFactory();

    private static final OrderDao orderDao = OrderDao.getInstance();
    private static final UserDao userDao = UserDao.getInstance();
    private static final AddressDao addressDao = AddressDao.getInstance();

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }

    @Test
    public void OrderDaoCRUD_NoFail() {
        try (var session = sessionFactory.openSession()){
            Order entity = Order.builder()
                    .user(userDao.findById(1, session).orElseThrow())
                    .address(addressDao.findById(1, session).orElseThrow())
                    .date(LocalDateTime.now().withNano(0))
                    .build();

            orderDao.save(entity, session);

            var foundEntity = orderDao.findById(entity.getId(), session).orElseThrow();
            assertEquals(entity, foundEntity);

            foundEntity.setDate(
                    foundEntity.getDate().plusHours(15)
            );
            orderDao.update(foundEntity, session);

            assertTrue(orderDao.delete(foundEntity, session));

        } catch (NullPointerException e) {
            fail("Something went wrong");
        } catch (Exception e) {
            fail("Connection error: " + e.getMessage());
        }
    }

}