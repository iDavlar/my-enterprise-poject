package by.davlar.jdbc.dao;

import by.davlar.jdbc.entity.Address;
import by.davlar.jdbc.utils.ConnectionManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressDaoTest {

    private static final AddressDao dao = AddressDao.getInstance();

    @Test
    public void AddressDaoCRUD_NoFail() {
        Address entity = Address.builder()
                .userId(1)
                .city("test")
                .region("test")
                .street("test")
                .apartment("15")
                .build();

        try (var connection = ConnectionManager.get()) {
            dao.save(entity, connection);

            var foundEntity = dao.findById(entity.getId(), connection).orElseThrow();
            assertEquals(entity, foundEntity);

            foundEntity.setCity("Test1");
            assertTrue(dao.update(foundEntity, connection));

            assertTrue(dao.delete(foundEntity.getId(), connection));

        } catch (NullPointerException e) {
            fail("Something went wrong");
        } catch (Exception e) {
            fail("Connection error: " + e.getMessage());
        }
    }
}
