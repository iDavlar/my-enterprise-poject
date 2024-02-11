package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.Order;
import by.davlar.hibernate.utils.ConfigurationManager;
import by.davlar.hibernate.utils.TestDataImporter;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class OrderRepositoryTest {

    private final SessionFactory sessionFactory = ConfigurationManager.getSessionFactory();

    private static final OrderRepository orderDao = OrderRepository.getInstance();
    private static final UserRepository userDao = UserRepository.getInstance();
    private static final AddressRepository addressDao = AddressRepository.getInstance();

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }

    @Test
    public void OrderDaoCRUD_NoFail() {
        try (var session = sessionFactory.openSession()){
            Order entity = Order.builder()
                    .user(userDao.findById(1, session).orElseThrow())
                    .address(addressDao.findById(1, session).orElseThrow())
                    .date(LocalDateTime.now().withNano(0))
                    .build();

            orderDao.save(entity, session);

            var foundEntity = orderDao.findById(entity.getId(), session).orElseThrow();
            assertEquals(entity, foundEntity);

            foundEntity.setDate(
                    foundEntity.getDate().plusHours(15)
            );
            orderDao.update(foundEntity, session);

            assertTrue(orderDao.delete(foundEntity, session));

        } catch (NullPointerException e) {
            fail("Something went wrong");
        } catch (Exception e) {
            fail("Connection error: " + e.getMessage());
        }
    }

    @Test
    public void findOrderOnDate(){
        @Cleanup var session = sessionFactory.openSession();

        List<Order> orders = orderDao.findOrderOnDate(LocalDate.of(2023, Month.OCTOBER, 1), session);
        assertThat(orders).hasSize(2);

        List<String> names = orders.stream()
                .map(order -> order.getUser().getFirstName())
                .toList();

        assertThat(names).containsExactlyInAnyOrder("Даниил", "Глеб");
    }

}