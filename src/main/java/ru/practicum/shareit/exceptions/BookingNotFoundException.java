package ru.practicum.shareit.exceptions;

import java.util.NoSuchElementException;

public class BookingNotFoundException extends NoSuchElementException {
    public BookingNotFoundException(long id) {
        super("Бронирование с id" + id + " не найдено.");
    }
}
