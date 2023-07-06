package ru.practicum.shareit.exceptions;

import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException {
    public UserNotFoundException(long id) {
        super("Пользователь с Id " + id + " не найден");
    }
}
