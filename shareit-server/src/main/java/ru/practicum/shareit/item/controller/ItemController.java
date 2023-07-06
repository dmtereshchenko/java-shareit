package ru.practicum.shareit.item.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoLong;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.shareit.Constant.getId;

@RestController
@Slf4j
@Validated
@AllArgsConstructor
@RequestMapping(path = "/items")
public class ItemController {

    private final ItemService service;

    @PostMapping()
    ItemDto create(@Valid @RequestBody ItemDto itemDto,
                   @RequestHeader(getId) long userId) {
        return service.create(itemDto, userId);
    }

    @PostMapping(value = "/{itemId}/comment")
    CommentDto addComment(@Valid @RequestBody CommentDto commentDto,
                          @RequestHeader(getId) long userId,
                          @PathVariable long itemId) {
        return service.addComment(commentDto, userId, itemId);
    }

    @PatchMapping(value = "/{itemId}")
    ItemDto update(@RequestBody ItemDto itemDto,
                   @RequestHeader(getId) long userId,
                   @PathVariable int itemId) {
        return service.update(itemDto, userId, itemId);
    }

    @GetMapping(value = "/{itemId}")
    ItemDtoLong get(@PathVariable long itemId,
                    @RequestHeader(getId) long userId) {
        return service.get(itemId, userId);
    }

    @GetMapping()
    List<ItemDtoLong> getAll(@RequestHeader(getId) long userId,
                             @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                             @RequestParam(defaultValue = "10") @Positive int size) {
        return service.getAll(userId, from, size);
    }

    @GetMapping(value = "/search")
    List<ItemDto> find(@RequestParam String text,
                       @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                       @RequestParam(defaultValue = "10") @Positive int size) {
        return service.find(text, from, size);
    }
}
