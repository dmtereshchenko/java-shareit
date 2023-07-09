package ru.practicum.shareit.exceptions;

import javax.xml.bind.ValidationException;

public class InvalidDataException extends ValidationException {
    public InvalidDataException(String message) {
        super(message);
    }
}