package ru.practicum.shareit.exceptions;

import java.util.NoSuchElementException;

public class ItemRequestNotFoundException extends NoSuchElementException {

    public ItemRequestNotFoundException(long id) {
        super("Запрос с id" + id + " не найден.");
    }
}
