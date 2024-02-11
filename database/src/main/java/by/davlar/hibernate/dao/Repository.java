package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Repository<K extends Serializable, E extends BaseEntity<K>> {
    E save(E entity);

    boolean delete(E entity);

    boolean delete(K id);

    List<E> findAll();

    Optional<E> findById(K id);

    E update(E entity);
}
