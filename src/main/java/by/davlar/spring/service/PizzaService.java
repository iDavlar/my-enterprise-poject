package by.davlar.spring.service;

import by.davlar.spring.database.repository.PizzaRepository;
import by.davlar.spring.dto.PizzaDto;
import by.davlar.spring.mapper.PizzaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final PizzaMapper pizzaMapper;

    Page<PizzaDto> findAll(Pageable pageable) {
        log.info("findAll(pageable = {})", pageable);
        return pizzaRepository.findAll(pageable)
                .map(pizzaMapper::PizzaToPizzaDto);
    }

}
