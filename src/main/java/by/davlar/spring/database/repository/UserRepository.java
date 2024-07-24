package by.davlar.spring.database.repository;

import by.davlar.spring.database.entity.User;
import by.davlar.spring.database.projection.OrdersSumPerUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

import static by.davlar.spring.database.utils.EntityGraphHelper.WITH_ROLE;

public interface UserRepository
        extends JpaRepository<User, Integer>,
        QuerydslPredicateExecutor<User> {

    @EntityGraph(value = WITH_ROLE)
    Optional<User> findByLoginAndPassword(String login, String password);

    @Override
    @EntityGraph(value = WITH_ROLE)
    Optional<User> findById(Integer integer);

    @Override
    @EntityGraph(value = WITH_ROLE)
    List<User> findAll();

    @Query(value = "select new by.davlar.spring.database.projection.OrdersSumPerUser(u, sum(oe.amount * p.cost)) " +
                   "from User u " +
                   "join Order o on u = o.user " +
                   "join OrderEntry  oe on o = oe.order " +
                   "join Pizza p on oe.pizza = p " +
                   "group by u")
    List<OrdersSumPerUser> findOrdersSumPerUser();
}
