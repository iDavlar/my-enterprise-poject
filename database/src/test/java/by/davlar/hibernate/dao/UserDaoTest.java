package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.User;
import by.davlar.hibernate.utils.ConfigurationManager;
import by.davlar.hibernate.utils.TestDataImporter;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class UserDaoTest {

    private final SessionFactory sessionFactory = ConfigurationManager.getSessionFactory();

    private static final UserDao userDao = UserDao.getInstance();
    private static final RoleDao roleDao = RoleDao.getInstance();

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }

    @Test
    public void UserDaoCRUD_NoFail() {
        try (var session = sessionFactory.openSession()){
            User entity = User.builder()
                    .firstName("Test")
                    .lastName("Test")
                    .birthday(Date.valueOf(LocalDate.from(LocalDateTime.now())))
                    .login("Test")
                    .password("1234567")
                    .telephone("1234567")
                    .role(roleDao.findById(roleDao.getDefaultId(), session).orElseThrow())
                    .build();

            userDao.save(entity, session);

            User foundEntity = userDao.findById(entity.getId(), session).orElseThrow();
            assertEquals(entity, foundEntity);

            foundEntity.setLogin("TestTest");
            userDao.update(foundEntity, session);

            assertTrue(userDao.delete(foundEntity, session));

        } catch (NullPointerException e) {
            fail("Something went wrong");
        } catch (Exception e) {
            fail("Connection error: " + e.getMessage());
        }
    }

}