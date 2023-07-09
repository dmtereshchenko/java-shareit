package ru.practicum.shareit.exceptions;

public class ActionNotFoundException extends RuntimeException {

    public ActionNotFoundException(String message) {
        super(message);
    }
}
