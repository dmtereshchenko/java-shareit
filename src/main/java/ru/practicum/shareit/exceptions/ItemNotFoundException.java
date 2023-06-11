package ru.practicum.shareit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends NoSuchElementException {
    public ItemNotFoundException(long id) {
        super("Вещь с id" + id + " не найдена.");
    }
}
