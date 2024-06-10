package by.davlar.spring.service;

import by.davlar.spring.annotation.IT;
import by.davlar.spring.dto.PizzaDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@IT
public class PizzaServiceTest {
    @Autowired
    private PizzaService pizzaService;

    @Test
    void findAllPageable_noThrow() {
        List<PizzaDto> pizzas = pizzaService.findAll(
                PageRequest.of(1, 2, Sort.by("name"))
        );

        assertFalse(pizzas.isEmpty());
    }
}
