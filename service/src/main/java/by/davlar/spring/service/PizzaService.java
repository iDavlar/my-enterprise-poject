package by.davlar.spring.service;

import by.davlar.spring.database.repository.PizzaRepository;
import by.davlar.spring.dto.PizzaDto;
import by.davlar.spring.mapper.PizzaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final PizzaMapper pizzaMapper = PizzaMapper.INSTANCE;

    List<PizzaDto> findAll(Pageable pageable) {
        return pizzaRepository.findAll(pageable)
                .map(pizzaMapper::PizzaToPizzaDto)
                .toList();
    }

}
