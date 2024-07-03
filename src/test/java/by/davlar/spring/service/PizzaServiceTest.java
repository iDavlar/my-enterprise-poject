package by.davlar.spring.service;

import by.davlar.spring.annotation.IT;
import by.davlar.spring.dto.PizzaDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
public class PizzaServiceTest {
    @Autowired
    private PizzaService pizzaService;

    @Test
    void findAll_AllPizzasWith3PizzaPerPageSortByName_ResultIsNotEmptyAndPageReadingNoFail() {
        PageRequest pageRequest = PageRequest.of(
                0,
                2,
                Sort.by("name")
        );
        Page<PizzaDto> pizzas = pizzaService.findAll(pageRequest);

        assertFalse(pizzas.isEmpty());
        assertThat(pizzas.getTotalPages()).isNotZero();
        assertThat(pizzas.getTotalElements()).isNotZero();

        try {
            pizzas.getContent().forEach(pizzaDto -> System.out.println(pizzaDto.getName()));
            while (pizzas.hasNext()) {
                pizzas = pizzaService.findAll(pizzas.nextPageable());
                pizzas.forEach(pizzaDto -> System.out.println(pizzaDto.getName()));
            }
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void findAll_AllPizzasWith3PizzaPerPageSortByCostDesc_ResultIsNotEmptyAndPageReadingNoFail() {
        PageRequest pageRequest = PageRequest.of(
                0,
                3,
                Sort.by("cost").descending()
        );

        Page<PizzaDto> pizzas = pizzaService.findAll(pageRequest);

        assertFalse(pizzas.isEmpty());
        assertThat(pizzas.getTotalPages()).isNotZero();
        assertThat(pizzas.getTotalElements()).isNotZero();
        
        try {
            pizzas.getContent().forEach(pizzaDto -> System.out.println(pizzaDto.getCost()));
            while (pizzas.hasNext()) {
                pizzas = pizzaService.findAll(pizzas.nextPageable());
                pizzas.forEach(pizzaDto -> System.out.println(pizzaDto.getCost()));
            }
        } catch (Exception e) {
            fail(e);
        }
    }
}
