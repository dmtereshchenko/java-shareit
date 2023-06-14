package ru.practicum.shareit.item.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoLong;
import ru.practicum.shareit.item.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(path = "/items")
public class ItemController {

    private final ItemService service;
    private final String getId = "X-Sharer-User-Id";


    @PostMapping()
    ItemDto create(@Valid @RequestBody ItemDto itemDto, @RequestHeader(getId) long userId, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.create(itemDto, userId);
    }

    @PostMapping(value = "/{itemId}/comment")
    CommentDto addComment(@Valid @RequestBody CommentDto commentDto, @RequestHeader(getId) long userId, @PathVariable long itemId, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.addComment(commentDto, userId, itemId);
    }

    @PatchMapping(value = "/{itemId}")
    ItemDto update(@RequestBody ItemDto itemDto, @RequestHeader(getId) long userId,
                   @PathVariable int itemId, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.update(itemDto, userId, itemId);
    }

    @GetMapping(value = "/{itemId}")
    ItemDtoLong get(@PathVariable long itemId, @RequestHeader(getId) long userId, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.get(itemId, userId);
    }

    @GetMapping()
    List<ItemDtoLong> getAll(@RequestHeader(getId) long userId, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.getAll(userId);
    }

    @GetMapping(value = "/search")
    List<ItemDto> find(@RequestParam String text, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.find(text);
    }
}
