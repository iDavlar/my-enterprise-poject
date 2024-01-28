package by.davlar.hibernate.dao;

import by.davlar.hibernate.dao.PizzaDao;
import by.davlar.hibernate.entity.Pizza;
import by.davlar.hibernate.utils.ConfigurationManager;
import by.davlar.hibernate.utils.TestDataImporter;
import by.davlar.jdbc.utils.ConnectionManager;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class PizzaDaoTest {

    private final SessionFactory sessionFactory = ConfigurationManager.getSessionFactory();

    private static final PizzaDao dao = PizzaDao.getInstance();

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }

    @Test
    public void PizzaDaoCRUD_NoFail() {

        Pizza entity = Pizza.builder()
                .name("Test")
                .cost(123)
                .build();

        try (var session = sessionFactory.openSession()){
            dao.save(entity, session);

            var foundEntity = dao.findById(entity.getId(), session).orElseThrow();
            assertEquals(entity, foundEntity);

            foundEntity.setName("Test1");
            dao.update(foundEntity, session);

            assertTrue(dao.delete(foundEntity, session));

        } catch (NullPointerException e) {
            fail("Something went wrong");
        } catch (Exception e) {
            fail("Connection error: " + e.getMessage());
        }
    }
}