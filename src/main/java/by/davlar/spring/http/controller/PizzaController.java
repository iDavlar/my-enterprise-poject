package by.davlar.spring.http.controller;

import by.davlar.spring.dto.PageResponse;
import by.davlar.spring.http.utils.TemplatePath;
import by.davlar.spring.service.PizzaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static by.davlar.spring.http.utils.AttributeHelper.*;
import static by.davlar.spring.http.utils.UrlPath.*;

@Controller
@RequestMapping(PIZZAS)
@RequiredArgsConstructor
public class PizzaController {
    private final PizzaService pizzaService;

    @GetMapping
    public String findAll(Model model, Pageable pageable) {
        model.addAttribute(MODEL.PIZZAS, PageResponse.of(pizzaService.findAll(pageable)));
        return TemplatePath.PIZZAS;
    }
}
