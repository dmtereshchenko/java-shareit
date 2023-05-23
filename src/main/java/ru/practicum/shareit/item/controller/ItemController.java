package ru.practicum.shareit.item.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
public class ItemController {

    private final ItemService service;

    @Autowired
    public ItemController(ItemService service) {
        this.service = service;
    }

    @PostMapping(value = "/items")
    Item create(@Valid @RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") int userId, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.create(itemDto, userId);
    }

    @PatchMapping(value = "/items/{id}")
    Item update(@RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") int userId,
                @PathVariable int id, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.update(itemDto, userId, id);
    }

    @GetMapping(value = "/items/{id}")
    Item get(@PathVariable int id, @RequestHeader("X-Sharer-User-Id") int userId) {
        return service.get(id, userId);
    }

    @GetMapping(value = "/items")
    List<Item> getAll(@RequestHeader("X-Sharer-User-Id") int userId) {
        return service.getAll(userId);
    }

    @GetMapping(value = "/items/search")
    Set<Item> find(@RequestParam String text) {
        return service.find(text);
    }
}
