package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.User;
import by.davlar.hibernate.utils.ConfigurationManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao extends AbstractDao<Integer, User> {
    private static final UserDao INSTANCE = new UserDao();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static UserDao getInstance() {
        return INSTANCE;
    }

    public Optional<User> findByLoginPassword(String login, String password) {
        log.debug("Start findByLoginPassword method with login = {} and password = {}", login, password);
        Configuration configuration = ConfigurationManager.getConfiguration();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            log.info("Start session {}", session);
            session.beginTransaction();

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
}
