package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static ru.practicum.shareit.Constant.getId;


@Controller
@Slf4j
@AllArgsConstructor
@Validated
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestClient client;

    @PostMapping
    ResponseEntity<Object> create(@Valid @RequestBody ItemRequestDto itemRequestDto,
                                  @RequestHeader(getId) long userId) {
        log.info("Получен запрос POST /requests/ от пользователя {}", userId);
        return client.create(itemRequestDto, userId);
    }

    @GetMapping("/{requestId}")
    ResponseEntity<Object> get(@RequestHeader(getId) long userId,
                               @PathVariable long requestId) {
        log.info("Получен запрос GET /requests/{} от пользователя {}", requestId, userId);
        return client.get(userId, requestId);
    }

    @GetMapping("/all")
    ResponseEntity<Object> getAll(@RequestHeader(getId) long userId,
                                  @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                  @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Получен запрос GET /requests/all/ от пользователя {}", userId);
        return client.getAll(userId, from, size);
    }

    @GetMapping
    ResponseEntity<Object> getAllByOwner(@RequestHeader(getId) long userId) {
        log.info("Получен запрос GET /requests/ от пользователя {}", userId);
        return client.getAllByOwner(userId);
    }
}
