package ru.practicum.shareit.request.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

import static ru.practicum.shareit.Constant.getId;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestService service;

    @PostMapping
    ItemRequestDto create(@RequestBody ItemRequestDto itemRequestDto,
                          @RequestHeader(getId) long userId) {
        log.info("Получен запрос POST /requests/ от пользователя {}", userId);
        return service.create(itemRequestDto, userId);
    }

    @GetMapping("/{requestId}")
    ItemRequestDto get(@RequestHeader(getId) long userId,
                       @PathVariable long requestId) {
        log.info("Получен запрос GET /requests/{} от пользователя {}", requestId, userId);
        return service.get(userId, requestId);
    }

    @GetMapping("/all")
    List<ItemRequestDto> getAll(@RequestHeader(getId) long userId,
                                @RequestParam int from,
                                @RequestParam int size) {
        log.info("Получен запрос GET /requests/all/ от пользователя {}", userId);
        return service.getAll(userId, from, size);
    }

    @GetMapping
    List<ItemRequestDto> getAllByOwner(@RequestHeader(getId) long userId) {
        log.info("Получен запрос GET /requests/ от пользователя {}", userId);
        return service.getAllByOwner(userId);
    }
}
