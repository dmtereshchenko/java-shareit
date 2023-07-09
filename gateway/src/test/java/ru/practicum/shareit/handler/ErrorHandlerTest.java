package ru.practicum.shareit.handler;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import ru.practicum.shareit.exceptions.ErrorHandler;
import ru.practicum.shareit.exceptions.ErrorResponse;
import ru.practicum.shareit.exceptions.InvalidDataException;

import javax.xml.bind.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorHandlerTest {

    @InjectMocks
    private ErrorHandler handler = new ErrorHandler();


    @Test
    void handleInvalidDataExceptionTest() {
        ValidationException e = new ValidationException("test");
        ErrorResponse expectedResponse = new ErrorResponse(e.getMessage());
        ErrorResponse actualResponse = handler.handleInvalidDataException(new InvalidDataException("test"));
        assertEquals(actualResponse.getError(), expectedResponse.getError());
    }
}
