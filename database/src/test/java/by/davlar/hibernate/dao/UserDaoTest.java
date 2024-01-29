package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.Order;
import by.davlar.hibernate.entity.User;
import by.davlar.hibernate.utils.ConfigurationManager;
import by.davlar.hibernate.utils.TestDataImporter;
import com.querydsl.core.Tuple;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
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
        try (var session = sessionFactory.openSession()) {
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

    @Test
    public void findAllOrdersByUser() {
        @Cleanup var session = sessionFactory.openSession();

        List<Order> users = userDao.findAllOrdersByUser(
                userDao.findById(1, session).orElseThrow(),
                session
        );
        assertThat(users).hasSize(2);

        List<LocalDateTime> dates = users.stream()
                .map(Order::getDate)
                .toList();

        assertThat(dates).contains(
                LocalDateTime.of(2023, Month.OCTOBER, 1, 0, 0),
                LocalDateTime.of(2023, Month.OCTOBER, 15, 0, 0)
        );
    }

    @Test
    public void findOrdersSumPerUser() {
        @Cleanup var session = sessionFactory.openSession();

        List<Tuple> results = userDao.findOrdersSumPerUser(session);
        assertThat(results).hasSize(5);

        List<String> users = results.stream()
                .map(t -> t.get(0, User.class).getFirstName())
                .toList();
        assertThat(users).containsExactlyInAnyOrder("Даниил", "Егор", "Глеб", "Анна", "Мария");

        List<Integer> sums = results.stream()
                .map(t -> t.get(1, Integer.class))
                .toList();
        assertThat(sums).containsExactlyInAnyOrder(2215, 6015, 1450, 1000, 2000);

    }

}