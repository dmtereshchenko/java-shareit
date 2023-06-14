package ru.practicum.shareit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends NoSuchElementException {
    public UserNotFoundException(long id) {
        super("Пользователь с Id " + id + " не найден");
    }
}
