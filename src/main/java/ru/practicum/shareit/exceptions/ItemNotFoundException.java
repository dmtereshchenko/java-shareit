package ru.practicum.shareit.exceptions;

import java.util.NoSuchElementException;

public class ItemNotFoundException extends NoSuchElementException {
    public ItemNotFoundException(long id) {
        super("Вещь с id" + id + " не найдена.");
    }
}
