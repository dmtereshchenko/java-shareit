package ru.practicum.shareit.item.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoLong;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

import static ru.practicum.shareit.Constant.getId;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(path = "/items")
public class ItemController {

    private final ItemService service;

    @PostMapping
    ItemDto create(@RequestBody ItemDto itemDto,
                   @RequestHeader(getId) long userId) {
        log.info("Получен запрос POST /items/ от пользователя {}", userId);
        return service.create(itemDto, userId);
    }

    @PostMapping(value = "/{itemId}/comment")
    CommentDto addComment(@RequestBody CommentDto commentDto,
                          @RequestHeader(getId) long userId,
                          @PathVariable long itemId) {
        log.info("Получен запрос POST /items/{}/comment от пользователя {}", itemId, userId);
        return service.addComment(commentDto, userId, itemId);
    }

    @PatchMapping(value = "/{itemId}")
    ItemDto update(@RequestBody ItemDto itemDto,
                   @RequestHeader(getId) long userId,
                   @PathVariable int itemId) {
        log.info("Получен запрос PATCH /items/{}/ от пользователя {}", itemId, userId);
        return service.update(itemDto, userId, itemId);
    }

    @GetMapping(value = "/{itemId}")
    ItemDtoLong get(@PathVariable long itemId,
                    @RequestHeader(getId) long userId) {
        log.info("Получен запрос /items/{}/ от пользователя {}", itemId, userId);
        return service.get(itemId, userId);
    }

    @GetMapping
    List<ItemDtoLong> getAll(@RequestHeader(getId) long userId,
                             @RequestParam int from,
                             @RequestParam int size) {
        log.info("Получен запрос GET /items/ от пользователя {}", userId);
        return service.getAll(userId, from, size);
    }

    @GetMapping(value = "/search")
    List<ItemDto> find(@RequestParam String text,
                       @RequestParam int from,
                       @RequestParam int size) {
        log.info("Получен запрос /items/search/{}", text);
        return service.find(text, from, size);
    }
}
