package by.davlar.spring.database.repository;

import by.davlar.spring.database.entity.Order;
import by.davlar.spring.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    LocalTime beginning = LocalTime.of(0, 0, 0);
    LocalTime ending = LocalTime.of(23, 59, 59);


    @Query(value = "select o, u from Order as o " +
                   "join User as u on o.user = u " +
                   "where o.date between ?#{@orderRepository.getBeginningDate([0])} " +
                   "and ?#{@orderRepository.getEndingDate([0])}")
    List<Order> findOrderOnDate(LocalDate date);

    List<Order> findAllByUser(User user);

    default LocalDateTime getBeginningDate(LocalDate date) {
        return LocalDateTime.of(date, beginning);
    }

    default LocalDateTime getEndingDate(LocalDate date) {
        return LocalDateTime.of(date, ending);
    }
}
