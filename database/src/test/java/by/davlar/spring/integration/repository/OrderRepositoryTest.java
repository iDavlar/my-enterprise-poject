package by.davlar.spring.integration.repository;

import by.davlar.spring.annotation.IT;
import by.davlar.spring.database.entity.Order;
import by.davlar.spring.database.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IT
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void findOrderOnDate(){

        List<Order> orders = orderRepository.findOrderOnDate(LocalDate.of(2023, Month.OCTOBER, 1));
        assertThat(orders).hasSize(2);

        List<String> names = orders.stream()
                .map(order -> order.getUser().getFirstName())
                .toList();

        assertThat(names).containsExactlyInAnyOrder("Даниил", "Глеб");
    }
}
