package by.davlar.spring.mapper;

import by.davlar.spring.database.entity.Pizza;
import by.davlar.spring.dto.PizzaDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PizzaMapper {

    PizzaMapper INSTANCE = Mappers.getMapper(PizzaMapper.class);

    Pizza PizzaDtoToPizza(PizzaDto pizzaDto);

    PizzaDto PizzaToPizzaDto(Pizza pizza);
}
