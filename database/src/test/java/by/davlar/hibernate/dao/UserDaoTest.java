package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private static final UserDao userDao = UserDao.getInstance();
    private static final RoleDao roleDao = RoleDao.getInstance();

    @Test
    public void UserDaoCRUD_NoFail() {
        try {
            User entity = User.builder()
                    .firstName("Test")
                    .lastName("Test")
                    .birthday(Date.valueOf(LocalDate.from(LocalDateTime.now())))
                    .login("Test")
                    .password("1234567")
                    .telephone("1234567")
                    .role(roleDao.findById(roleDao.getDefaultId()).orElseThrow())
                    .build();

            userDao.save(entity);

            User foundEntity = userDao.findById(entity.getId()).orElseThrow();
            assertEquals(entity, foundEntity);

            foundEntity.setLogin("TestTest");
            userDao.update(foundEntity);

            assertTrue(userDao.delete(foundEntity));

        } catch (NullPointerException e) {
            fail("Something went wrong");
        } catch (Exception e) {
            fail("Connection error: " + e.getMessage());
        }
    }

}