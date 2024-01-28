package by.davlar.hibernate.dao;

import by.davlar.hibernate.utils.ConfigurationManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<K extends Serializable, E>
        implements Dao<K, E>, ConnectableDao<K, E> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private Class<E> entityClass;

    protected Class<E> getEntityClass() {
        log.trace("EntityClass request");
        if (entityClass == null) {
            //noinspection unchecked
            entityClass = (Class<E>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[1];
            log.trace("EntityClass lazy initialisation");
        }
        log.debug("entityClass: {}", entityClass);
        return entityClass;
    }

    @Override
    public E save(E entity) {
        try (var sessionFactory = ConfigurationManager.getSessionFactory();
             var session = sessionFactory.openSession()) {
            save(entity, session);
        }
        return entity;
    }

    @Override
    public boolean delete(E entity) {
        try (var sessionFactory = ConfigurationManager.getSessionFactory();
             var session = sessionFactory.openSession()) {
            return delete(entity, session);
        }
    }

    @Override
    public List<E> findAll() {
        try (var sessionFactory = ConfigurationManager.getSessionFactory();
             var session = sessionFactory.openSession()) {
            return findAll(session);
        }
    }

    @Override
    public Optional<E> findById(K id) {
        try (var sessionFactory = ConfigurationManager.getSessionFactory();
             var session = sessionFactory.openSession()) {
            return findById(id, session);
        }
    }

    @Override
    public E update(E entity) {
        try (var sessionFactory = ConfigurationManager.getSessionFactory();
             var session = sessionFactory.openSession()) {
            return update(entity, session);
        }
    }

    @Override
    public E save(E entity, Session session) {
        log.debug("Start save method with entity = {}", entity);
        try {
            log.info("Start session {}", session);
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            log.info("Commit session {}", session);
        } catch (Exception e) {
            log.error("Error during saving:", e);
            throw e;
        }

        log.trace("End save method and return {}", entity);
        return entity;
    }

    @Override
    public boolean delete(E entity, Session session) {
        log.debug("Start delete method with entity = {}", entity);
        boolean result = true;
        try {
            log.info("Start session {}", session);
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
            log.info("Commit session {}", session);
        } catch (Exception e) {
            log.error("Error during deletion:", e);
            result = false;
        }

        log.trace("End save method and return {}", result);
        return result;
    }

    @Override
    public List<E> findAll(Session session) {
        log.debug("Start findAll method");
        try {
            log.info("Start session {}", session);
            session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<E> cq = cb.createQuery(getEntityClass());
            Root<E> rootEntry = cq.from(getEntityClass());
            CriteriaQuery<E> all = cq.select(rootEntry);

            TypedQuery<E> allQuery = session.createQuery(all);
            List<E> entities = allQuery.getResultList();

            session.getTransaction().commit();
            log.info("Commit session {}", session);
            log.trace("End findAll method and return {}", entities);
            return entities;
        } catch (Exception e) {
            log.error("Error when searching for all elements:", e);
            throw e;
        }
    }

    @Override
    public Optional<E> findById(K id, Session session) {
        log.debug("Start findById method with id = {}", id);
        try {
            log.info("Start session {}", session);
            session.beginTransaction();
            Optional<E> result = Optional.ofNullable(session.get(getEntityClass(), id));
            session.getTransaction().commit();
            log.info("Commit session {}", session);
            log.trace("End findById method and return {}", result);
            return result;
        } catch (Exception e) {
            log.error("Error when searching for element by id:", e);
            throw e;
        }
    }

    @Override
    public E update(E entity, Session session) {
        log.debug("Start update method with entity = {}", entity);
        try {
            log.info("Start session {}", session);
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
            log.info("Commit session {}", session);
        } catch (Exception e) {
            log.error("Error during update:", e);
            throw e;
        }
        log.trace("End update method and return {}", entity);
        return entity;
    }
}
