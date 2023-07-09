package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.exceptions.InvalidDataException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static ru.practicum.shareit.Constant.getId;


@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient client;

    @PostMapping
    ResponseEntity<Object> create(@Valid @RequestBody BookingDto bookingDto,
                                  @RequestHeader(getId) Long userId) {
        log.info("Получен запрос POST /bookings/ от пользователя {}", userId);
        return client.create(userId, bookingDto);
    }

    @PatchMapping(value = "/{bookingId}")
    ResponseEntity<Object> update(@PathVariable Long bookingId,
                                  @RequestParam boolean approved,
                                  @RequestHeader(getId) Long userId) {
        log.info("Получен запрос PATCH /booking/{}/ от пользователя {}", bookingId, userId);
        return client.update(approved, bookingId, userId);
    }

    @GetMapping(value = "/{bookingId}")
    ResponseEntity<Object> get(@PathVariable long bookingId,
                               @RequestHeader(getId) long userId) {
        log.info("Получен запрос GET /bookings/{}/ от пользователя {}", bookingId, userId);
        return client.get(bookingId, userId);
    }

    @GetMapping
    ResponseEntity<Object> getAllByBooker(@RequestParam(defaultValue = "ALL") String state,
                                          @RequestHeader(getId) long userId,
                                          @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                          @RequestParam(defaultValue = "10") @Positive int size) throws InvalidDataException {
        log.info("Получен запрос GET /bookings/ от пользователя {}", userId);
        return client.getAllByBooker(BookingState.from(state).orElseThrow(() -> new InvalidDataException("Unknown state: " + state)),
                userId, from, size);
    }

    @GetMapping(value = "/owner")
    ResponseEntity<Object> getAllByOwner(@RequestParam(defaultValue = "ALL") String state,
                                         @RequestHeader(getId) long userId,
                                         @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                         @RequestParam(defaultValue = "10") @Positive int size) throws InvalidDataException {
        log.info("Получен запрос GET /bookings/owner/ от пользователя {}", userId);
        return client.getAllByOwner(BookingState.from(state).orElseThrow(() -> new InvalidDataException("Unknown state: " + state)), userId, from, size);
    }
}
