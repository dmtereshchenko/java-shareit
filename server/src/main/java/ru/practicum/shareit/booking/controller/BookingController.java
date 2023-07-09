package ru.practicum.shareit.booking.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exceptions.InvalidDataException;

import java.util.List;

import static ru.practicum.shareit.Constant.getId;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService service;

    @PostMapping
    BookingDto create(@RequestBody BookingDto bookingDto,
                      @RequestHeader(getId) long userId) {
        log.info("Получен запрос POST /bookings/ от пользователя {}", userId);
        return service.create(bookingDto, userId);
    }

    @PatchMapping(value = "/{bookingId}")
    BookingDto update(@PathVariable long bookingId,
                      @RequestParam boolean approved,
                      @RequestHeader(getId) long userId) {
        log.info("Получен запрос PATCH /booking/{}/ от пользователя {}", bookingId, userId);
        return service.update(bookingId, approved, userId);
    }

    @GetMapping(value = "/{bookingId}")
    BookingDto get(@PathVariable long bookingId,
                   @RequestHeader(getId) long userId) {
        log.info("Получен запрос GET /bookings/{}/ от пользователя {}", bookingId, userId);
        return service.get(bookingId, userId);
    }

    @GetMapping
    List<BookingDto> getAllByBooker(@RequestParam(defaultValue = "ALL") String state,
                                    @RequestHeader(getId) long userId,
                                    @RequestParam int from,
                                    @RequestParam int size) throws InvalidDataException {
        log.info("Получен запрос GET /bookings/ от пользователя {}", userId);
        return service.getAllByBooker(state, userId, from, size);
    }

    @GetMapping(value = "/owner")
    List<BookingDto> getAllByOwner(@RequestParam(defaultValue = "ALL") String state,
                                   @RequestHeader(getId) long userId,
                                   @RequestParam int from,
                                   @RequestParam int size) throws InvalidDataException {
        log.info("Получен запрос GET /bookings/owner/ от пользователя {}", userId);
        return service.getAllByOwner(state, userId, from, size);
    }
}
