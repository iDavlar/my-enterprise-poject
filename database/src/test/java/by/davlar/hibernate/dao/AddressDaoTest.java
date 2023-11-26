package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

class AddressDaoTest {

    private static final AddressDao dao = AddressDao.getInstance();
    private static final UserDao userDao = UserDao.getInstance();
    @Test
    public void AddressDaoCRUD_NoFail() {
        try {
            Address entity = Address.builder()
                    .city("test")
                    .region("test")
                    .street("test")
                    .apartment("15")
                    .user(userDao.findById(1).orElseThrow())
                    .build();

            dao.save(entity);

            var foundEntity = dao.findById(entity.getId()).orElseThrow();
            assertEquals(entity, foundEntity);

            foundEntity.setCity("Test1");
            dao.update(foundEntity);

            assertTrue(dao.delete(foundEntity));

        } catch (NullPointerException e) {
            fail("Something went wrong");
        } catch (Exception e) {
            fail("Connection error: " + e.getMessage());
        }
    }
}