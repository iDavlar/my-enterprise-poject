package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.Order;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static by.davlar.hibernate.entity.QOrder.order;
import static by.davlar.hibernate.entity.QUser.user;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderRepository extends BaseRepository<Integer, Order> {
    private static final OrderRepository INSTANCE = new OrderRepository();

    public static OrderRepository getInstance() {
        return INSTANCE;
    }

    public List<Order> findOrderOnDate(LocalDate date, Session session) {
        LocalTime beginning = LocalTime.of(0, 0, 0);
        LocalTime ending = LocalTime.of(23, 59, 59);
        return new JPAQuery<Order>(session)
                .select(order)
                .from(order)
                .join(order.user, user)
                .fetchJoin()
                .where(order.date.between(
                                LocalDateTime.of(date, beginning),
                                LocalDateTime.of(date, ending)
                        )
                )
                .fetch();
    }
}
