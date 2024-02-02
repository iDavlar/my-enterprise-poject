package by.davlar.jdbc.dao;

import by.davlar.jdbc.entity.Order;
import by.davlar.jdbc.utils.ConnectionManager;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDaoTest {

    private static final OrderDao dao = OrderDao.getInstance();

//    @Test
    public void OrderDaoCRUD_NoFail() {
        Order entity = Order.builder()
                .userId(1)
                .addressId(1)
                .date(Timestamp.valueOf(LocalDateTime.now().withNano(0)))
                .build();

        try (var connection = ConnectionManager.get()) {
            dao.save(entity, connection);

            var foundEntity = dao.findById(entity.getId(), connection).orElseThrow();
            assertEquals(entity, foundEntity);

            foundEntity.setDate(
                    Timestamp.valueOf(
                            foundEntity.getDate().toLocalDateTime().plusHours(15)
                    )
            );
            assertTrue(dao.update(foundEntity, connection));

            assertTrue(dao.delete(foundEntity.getId(), connection));

        } catch (NullPointerException e) {
            fail("Something went wrong");
        } catch (Exception e) {
            fail("Connection error: " + e.getMessage());
        }
    }
}
