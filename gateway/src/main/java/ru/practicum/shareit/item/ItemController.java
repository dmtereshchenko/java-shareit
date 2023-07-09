package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static ru.practicum.shareit.Constant.getId;

@Controller
@Slf4j
@Validated
@AllArgsConstructor
@RequestMapping(path = "/items")
public class ItemController {

    private final ItemClient client;

    @PostMapping
    ResponseEntity<Object> create(@Valid @RequestBody ItemDto itemDto,
                                  @RequestHeader(getId) long userId) {
        log.info("Получен запрос POST /items/ от пользователя {}", userId);
        return client.create(userId, itemDto);
    }

    @PostMapping(value = "/{itemId}/comment")
    ResponseEntity<Object> addComment(@Valid @RequestBody CommentDto commentDto,
                                      @RequestHeader(getId) long userId,
                                      @PathVariable long itemId) {
        log.info("Получен запрос POST /items/{}/comment от пользователя {}", itemId, userId);
        return client.addComment(userId, itemId, commentDto);
    }

    @PatchMapping(value = "/{itemId}")
    ResponseEntity<Object> update(@RequestBody ItemDto itemDto,
                                  @RequestHeader(getId) long userId,
                                  @PathVariable int itemId) {
        log.info("Получен запрос PATCH /items/{}/ от пользователя {}", itemId, userId);
        return client.update(userId, itemId, itemDto);
    }

    @GetMapping(value = "/{itemId}")
    ResponseEntity<Object> get(@PathVariable long itemId,
                               @RequestHeader(getId) long userId) {
        log.info("Получен запрос /items/{}/ от пользователя {}", itemId, userId);
        return client.get(userId, itemId);
    }

    @GetMapping
    ResponseEntity<Object> getAll(@RequestHeader(getId) long userId,
                                  @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                  @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Получен запрос GET /items/ от пользователя {}", userId);
        return client.getAll(userId, from, size);
    }

    @GetMapping(value = "/search")
    ResponseEntity<Object> find(@RequestHeader(getId) long userId,
                                @RequestParam String text,
                                @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Получен запрос /items/search/{}", text);
        return client.find(userId, from, size, text);
    }
}
