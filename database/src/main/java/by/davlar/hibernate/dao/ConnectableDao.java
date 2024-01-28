package by.davlar.hibernate.dao;

import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface ConnectableDao<K extends Serializable, E> {
    E save(E entity, Session session);

    boolean delete(E entity, Session session);

    List<E> findAll(Session session);

    Optional<E> findById(K id, Session session);

    E update(E entity, Session session);
}
