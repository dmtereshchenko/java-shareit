package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exceptions.InvalidDataException;

import java.util.List;

public interface BookingService {

    BookingDto create(BookingDto bookingDto, long userId);

    BookingDto update(long bookingId, boolean approved, long userId);

    BookingDto get(long bookingId, long userId);

    List<BookingDto> getAllByBooker(String state, long userId) throws InvalidDataException;

    List<BookingDto> getAllByOwner(String state, long userId) throws InvalidDataException;
}
