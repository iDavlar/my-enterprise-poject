package by.davlar.jdbc.dao;

import by.davlar.jdbc.entity.OrderEntry;
import by.davlar.jdbc.utils.ConnectionManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderEntryDaoTest {

    private static final OrderEntryDao dao = OrderEntryDao.getInstance();

    @Test
    public void OrderEntryDaoCRUD_NoFail() {
        OrderEntry entity = OrderEntry.builder()
                .orderId(1)
                .pizzaId(1)
                .amount(15)
                .build();

        try (var connection = ConnectionManager.get()) {
            dao.save(entity, connection);

            var foundEntity = dao.findById(entity.getId(), connection).orElseThrow();
            assertEquals(entity, foundEntity);

            foundEntity.setAmount(20);
            assertTrue(dao.update(foundEntity, connection));

            assertTrue(dao.delete(foundEntity.getId(), connection));

        } catch (NullPointerException e) {
            fail("Something went wrong");
        } catch (Exception e) {
            fail("Connection error: " + e.getMessage());
        }
    }
}
