package by.davlar.hibernate.dao;

import by.davlar.hibernate.dao.PizzaDao;
import by.davlar.hibernate.entity.Pizza;
import by.davlar.jdbc.utils.ConnectionManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PizzaDaoTest {

    private static final PizzaDao dao = PizzaDao.getInstance();
    @Test
    public void PizzaDaoCRUD_NoFail() {

        Pizza entity = Pizza.builder()
                .name("Test")
                .cost(123)
                .build();

        try {
            dao.save(entity);

            var foundEntity = dao.findById(entity.getId()).orElseThrow();
            assertEquals(entity, foundEntity);

            foundEntity.setName("Test1");
            dao.update(foundEntity);

            assertTrue(dao.delete(foundEntity));

        } catch (NullPointerException e) {
            fail("Something went wrong");
        } catch (Exception e) {
            fail("Connection error: " + e.getMessage());
        }
    }
}