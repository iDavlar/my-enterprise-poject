package by.davlar.spring.integration.repository;


import by.davlar.spring.annotation.IT;
import by.davlar.spring.database.projection.OrdersSumPerUser;
import by.davlar.spring.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IT
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findOrdersSumPerUser() {

        List<OrdersSumPerUser> results = userRepository.findOrdersSumPerUser();
        assertThat(results).hasSize(5);

        List<String> users = results.stream()
                .map(o -> o.user().getFirstName())
                .toList();
        assertThat(users).containsExactlyInAnyOrder("Даниил", "Егор", "Глеб", "Анна", "Мария");

        List<Long> sums = results.stream()
                .map(OrdersSumPerUser::sum)
                .toList();
        assertThat(sums).containsExactlyInAnyOrder(2215L, 6015L, 1450L, 1000L, 2000L);

    }
}
