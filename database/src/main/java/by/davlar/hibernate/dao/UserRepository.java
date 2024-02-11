package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.*;
import by.davlar.hibernate.utils.ConfigurationManager;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static by.davlar.hibernate.entity.QOrder.order;
import static by.davlar.hibernate.entity.QOrderEntry.orderEntry;
import static by.davlar.hibernate.entity.QPizza.pizza;
import static by.davlar.hibernate.entity.QUser.user;
import static by.davlar.hibernate.utils.FetchProfileHelper.WITH_ROLE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRepository extends BaseRepository<Integer, User> {
    private static final UserRepository INSTANCE = new UserRepository();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static UserRepository getInstance() {
        return INSTANCE;
    }

    public Optional<User> findByLoginPassword(String login, String password) {
        log.debug("Start findByLoginPassword method with login = {} and password = {}", login, password);
        Configuration configuration = ConfigurationManager.getConfiguration();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            log.info("Start session {}", session);
            session.beginTransaction();

            session.enableFetchProfile(WITH_ROLE);

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(getEntityClass());
            Root<User> rootEntry = cq.from(getEntityClass());
            cq.select(rootEntry);
            cq.where(cb.equal(rootEntry.get("login"), login),
                    cb.equal(rootEntry.get("password"), password));

            TypedQuery<User> allQuery = session.createQuery(cq);

            Optional<User> result = allQuery.getResultStream().findFirst();
            session.getTransaction().commit();
            log.info("Commit session {}", session);
            log.trace("End findByLoginPassword method and return {}", result);
            return result;
        } catch (Exception e) {
            log.error("Error when searching for element by id:", e);
            throw e;
        }
    }

    public List<Order> findAllOrdersByUser (User owner, Session session) {

        return new JPAQuery<Order>(session)
                .select(order)
                .from(order)
                .where(order.user.eq(owner))
                .fetch();
    }

    public List<Tuple> findOrdersSumPerUser (Session session) {

        return new JPAQuery<Tuple>(session)
                .select(user, orderEntry.amount.multiply(pizza.cost).sum())
                .from(user)
                .join(user.orders, order)
                .join(order.entries, orderEntry)
                .join(orderEntry.pizza, pizza)
                .groupBy(user)
                .fetch();
    }
}
