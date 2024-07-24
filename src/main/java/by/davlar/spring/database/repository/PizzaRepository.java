package by.davlar.spring.database.repository;

import by.davlar.spring.database.entity.Pizza;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PizzaRepository
        extends JpaRepository<Pizza, Integer>, QuerydslPredicateExecutor<Pizza> {
}
