package by.davlar.spring.mapper;

import by.davlar.spring.database.entity.Pizza;
import by.davlar.spring.dto.PizzaDto;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public abstract class PizzaMapper {

//    PizzaMapper INSTANCE = Mappers.getMapper(PizzaMapper.class);

    public abstract Pizza PizzaDtoToPizza(PizzaDto pizzaDto);

    public abstract PizzaDto PizzaToPizzaDto(Pizza pizza);
}
