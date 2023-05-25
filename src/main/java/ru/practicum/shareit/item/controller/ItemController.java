package ru.practicum.shareit.item.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class ItemController {

    private final ItemService service;
    private final String GETID = "X-Sharer-User-Id";

    @Autowired
    public ItemController(ItemService service) {
        this.service = service;
    }

    @PostMapping(value = "/items")
    ItemDto create(@Valid @RequestBody ItemDto itemDto, @RequestHeader(GETID) int userId, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.create(itemDto, userId);
    }

    @PatchMapping(value = "/items/{id}")
    ItemDto update(@RequestBody ItemDto itemDto, @RequestHeader(GETID) int userId,
                   @PathVariable int id, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.update(itemDto, userId, id);
    }

    @GetMapping(value = "/items/{id}")
    ItemDto get(@PathVariable int id, @RequestHeader(GETID) int userId) {
        return service.get(id, userId);
    }

    @GetMapping(value = "/items")
    List<ItemDto> getAll(@RequestHeader(GETID) int userId) {
        return service.getAll(userId);
    }

    @GetMapping(value = "/items/search")
    List<ItemDto> find(@RequestParam String text) {
        return service.find(text);
    }
}
