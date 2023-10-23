package by.davlar.jdbc.dao;

import by.davlar.jdbc.entity.Pizza;
import by.davlar.jdbc.utils.ConnectionManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PizzaDaoTest {

    private static final PizzaDao dao = PizzaDao.getInstance();

    @Test
    public void PizzaDaoCRUD_NoFail() {
        Pizza entity = Pizza.builder()
                .name("Test")
                .cost(123)
                .build();

        try (var connection = ConnectionManager.get()) {
            dao.save(entity, connection);

            var foundEntity = dao.findById(entity.getId(), connection).orElseThrow();
            assertEquals(entity, foundEntity);

            foundEntity.setName("Test1");
            assertTrue(dao.update(foundEntity, connection));

            assertTrue(dao.delete(foundEntity.getId(), connection));

        } catch (NullPointerException e) {
            fail("Something went wrong");
        } catch (Exception e) {
            fail("Connection error: " + e.getMessage());
        }
    }
}
