package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.OrderEntry;
import by.davlar.hibernate.utils.ConfigurationManager;
import by.davlar.hibernate.utils.TestDataImporter;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class OrderEntryRepositoryTest {

    private final SessionFactory sessionFactory = ConfigurationManager.getSessionFactory();

    private static final OrderEntryRepository orderEntryDao = OrderEntryRepository.getInstance();
    private static final OrderRepository orderDao = OrderRepository.getInstance();
    private static final PizzaRepository pizzaDao = PizzaRepository.getInstance();

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }

    @Test
    public void OrderEntryDaoCRUD_NoFail() {
        try  (var session = sessionFactory.openSession()){
            OrderEntry entity = OrderEntry.builder()
                    .order(orderDao.findById(1, session).orElseThrow())
                    .pizza(pizzaDao.findById(2, session).orElseThrow())
                    .amount(15)
                    .build();

            orderEntryDao.save(entity, session);

            var foundEntity = orderEntryDao.findById(entity.getId(), session).orElseThrow();
            assertEquals(entity, foundEntity);

            foundEntity.setAmount(20);
            orderEntryDao.update(foundEntity, session);

            assertTrue(orderEntryDao.delete(foundEntity, session));

        } catch (NullPointerException e) {
            fail("Something went wrong");
        } catch (Exception e) {
            fail("Connection error: " + e.getMessage());
        }
    }

}