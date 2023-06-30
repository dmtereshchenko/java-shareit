package ru.practicum.shareit.request.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.shareit.Constant.getId;

@RestController
@Slf4j
@AllArgsConstructor
@Validated
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestService service;

    @PostMapping()
    ItemRequestDto create(@Valid @RequestBody ItemRequestDto itemRequestDto,
                          @RequestHeader(getId) long userId,
                          HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.create(itemRequestDto, userId);
    }

    @GetMapping("/{requestId}")
    ItemRequestDto get(@RequestHeader(getId) long userId,
                       @PathVariable long requestId,
                       HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.get(userId, requestId);
    }

    @GetMapping("/all")
    List<ItemRequestDto> getAll(@RequestHeader(getId) long userId,
                                @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                @RequestParam(defaultValue = "10") @Positive int size,
                                HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.getAll(userId, from, size);
    }

    @GetMapping()
    List<ItemRequestDto> getAllByOwner(@RequestHeader(getId) long userId,
                                       HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.getAllByOwner(userId);
    }
}
