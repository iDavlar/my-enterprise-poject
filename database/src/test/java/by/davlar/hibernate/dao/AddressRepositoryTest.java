package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.Address;
import by.davlar.hibernate.utils.ConfigurationManager;
import by.davlar.hibernate.utils.TestDataImporter;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class AddressRepositoryTest {

    private final SessionFactory sessionFactory = ConfigurationManager.getSessionFactory();

    private static final AddressRepository dao = AddressRepository.getInstance();
    private static final UserRepository userDao = UserRepository.getInstance();

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }

    @Test
    public void AddressDaoCRUD_NoFail() {
        try (var session = sessionFactory.openSession()){
            Address entity = Address.builder()
                    .city("test")
                    .region("test")
                    .street("test")
                    .apartment("15")
                    .user(userDao.findById(1, session).orElseThrow())
                    .build();

            dao.save(entity, session);

            var foundEntity = dao.findById(entity.getId(), session).orElseThrow();
            assertEquals(entity, foundEntity);

            foundEntity.setCity("Test1");
            dao.update(foundEntity, session);

            assertTrue(dao.delete(foundEntity, session));

        } catch (NullPointerException e) {
            fail("Something went wrong");
        } catch (Exception e) {
            fail("Connection error: " + e.getMessage());
        }
    }
}