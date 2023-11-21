package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.Role;
import by.davlar.hibernate.entity.User;
import by.davlar.hibernate.utils.ConfigurationManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleDao extends AbstractDao<Integer, Role> {
    private static final RoleDao INSTANCE = new RoleDao();
    private static final Integer DEFAULT_ID = 2;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static RoleDao getInstance() {
        return INSTANCE;
    }

    public Integer getDefaultId() {
        return DEFAULT_ID;
    }

    public Optional<Role> findByName(String name) {
        log.debug("Start findByName method with name = {}", name);
        Configuration configuration = ConfigurationManager.getConfiguration();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            log.info("Start session {}", session);
            session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Role> cq = cb.createQuery(getEntityClass());
            Root<Role> rootEntry = cq.from(getEntityClass());
            cq.select(rootEntry);
            cq.where(cb.equal(rootEntry.get("name"), name));

            TypedQuery<Role> allQuery = session.createQuery(cq);

            Optional<Role> result = allQuery.getResultStream().findFirst();
            session.getTransaction().commit();
            log.info("Commit session {}", session);
            log.trace("End findByName method and return {}", result);
            return result;
        } catch (Exception e) {
            log.error("Error when searching for element by name:", e);
            throw e;
        }
    }
}
