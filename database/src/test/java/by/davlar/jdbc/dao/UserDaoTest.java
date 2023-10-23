package by.davlar.jdbc.dao;

import by.davlar.jdbc.entity.User;
import by.davlar.jdbc.utils.ConnectionManager;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {

    private static final UserDao dao = UserDao.getInstance();

    @Test
    public void UserDaoCRUD_NoFail() {
        User entity = User.builder()
                .firstName("Test")
                .lastName("Test")
                .birthday(Date.valueOf(LocalDate.from(LocalDateTime.now())))
                .login("Test")
                .password("1234567")
                .telephone("1234567")
                .build();

        try (var connection = ConnectionManager.get()) {
            dao.save(entity, connection);

            User foundEntity = dao.findById(entity.getId(), connection).orElseThrow();
            assertEquals(entity, foundEntity);

            foundEntity.setLogin("TestTest");
            assertTrue(dao.update(foundEntity, connection));

            assertTrue(dao.delete(foundEntity.getId(), connection));

        } catch (NullPointerException e) {
            fail("Something went wrong");
        } catch (Exception e) {
            fail("Connection error: " + e.getMessage());
        }
    }
}
