package ru.practicum.shareit.booking.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.Constant;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exceptions.InvalidDataException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@Validated
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService service;

    @PostMapping()
    BookingDto create(@Valid @RequestBody BookingDto bookingDto,
                      @RequestHeader(Constant.userId) long userId,
                      HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.create(bookingDto, userId);
    }

    @PatchMapping(value = "/{bookingId}")
    BookingDto update(@PathVariable long bookingId,
                      @RequestParam boolean approved,
                      @RequestHeader(Constant.userId) long userId,
                      HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.update(bookingId, approved, userId);
    }

    @GetMapping(value = "/{bookingId}")
    BookingDto get(@PathVariable long bookingId,
                   @RequestHeader(Constant.userId) long userId,
                   HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.get(bookingId, userId);
    }

    @GetMapping()
    List<BookingDto> getAllByBooker(@RequestParam(defaultValue = "ALL") String state,
                                    @RequestHeader(Constant.userId) long userId,
                                    @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                    @RequestParam(defaultValue = "10") @Positive int size,
                                    HttpServletRequest request) throws InvalidDataException {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.getAllByBooker(state, userId, from, size);
    }

    @GetMapping(value = "/owner")
    List<BookingDto> getAllByOwner(@RequestParam(defaultValue = "ALL") String state,
                                   @RequestHeader(Constant.userId) long userId,
                                   @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                   @RequestParam(defaultValue = "10") @Positive int size,
                                   HttpServletRequest request) throws InvalidDataException {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.getAllByOwner(state, userId, from, size);
    }
}
