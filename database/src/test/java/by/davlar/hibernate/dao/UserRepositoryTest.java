package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.Order;
import by.davlar.hibernate.entity.User;
import by.davlar.hibernate.utils.ConfigurationManager;
import by.davlar.hibernate.utils.FetchProfileHelper;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class UserRepositoryTest {

    private final SessionFactory sessionFactory = ConfigurationManager.getSessionFactory();

    private static final UserRepository userDao = UserRepository.getInstance();
    private static final RoleRepository roleDao = RoleRepository.getInstance();

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

        session.enableFetchProfile("withRole");

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

    @Test
    public void secondLevelCache() {
        @Cleanup var session1 = sessionFactory.openSession();
        @Cleanup var session2 = sessionFactory.openSession();

        session1.enableFetchProfile(FetchProfileHelper.WITH_ROLE);
//        session2.enableFetchProfile(FetchProfileHelper.WITH_ROLE);

        var user1 = userDao.findById(1, session1);
        var user2 = userDao.findById(1, session2);

        System.out.println(user1.hashCode());
        System.out.println(user2.hashCode());
    }

}