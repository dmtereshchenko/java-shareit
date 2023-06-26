package ru.practicum.shareit.handler;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import ru.practicum.shareit.exceptions.AccessDeniedException;
import ru.practicum.shareit.exceptions.ErrorHandler;
import ru.practicum.shareit.exceptions.ErrorResponse;

import javax.xml.bind.ValidationException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorHandlerTest {

    @InjectMocks
    private ErrorHandler handler = new ErrorHandler();

    @Test
    void handleAccessDeniedExceptionTest() {
        RuntimeException e = new RuntimeException("test");
        ErrorResponse expectedResponse = new ErrorResponse(e.getMessage());
        ErrorResponse actualResponse = handler.handleAccessDeniedException(new AccessDeniedException("test"));
        assertEquals(actualResponse.getError(), expectedResponse.getError());
    }

    @Test
    void handleActionNotFoundExceptionTest() {
        RuntimeException e = new RuntimeException("test");
        ErrorResponse expectedResponse = new ErrorResponse(e.getMessage());
        ErrorResponse actualResponse = handler.handleAccessDeniedException(new AccessDeniedException("test"));
        assertEquals(actualResponse.getError(), expectedResponse.getError());
    }

    @Test
    void handleBookingNotFoundExceptionTest() {
        NoSuchElementException e = new NoSuchElementException("test");
        ErrorResponse expectedResponse = new ErrorResponse(e.getMessage());
        ErrorResponse actualResponse = handler.handleAccessDeniedException(new AccessDeniedException("test"));
        assertEquals(actualResponse.getError(), expectedResponse.getError());
    }

    @Test
    void handleInvalidDataExceptionTest() {
        ValidationException e = new ValidationException("test");
        ErrorResponse expectedResponse = new ErrorResponse(e.getMessage());
        ErrorResponse actualResponse = handler.handleAccessDeniedException(new AccessDeniedException("test"));
        assertEquals(actualResponse.getError(), expectedResponse.getError());
    }

    @Test
    void handleItemNotFoundExceptionTest() {
        NoSuchElementException e = new NoSuchElementException("test");
        ErrorResponse expectedResponse = new ErrorResponse(e.getMessage());
        ErrorResponse actualResponse = handler.handleAccessDeniedException(new AccessDeniedException("test"));
        assertEquals(actualResponse.getError(), expectedResponse.getError());
    }

    @Test
    void handleItemRequestNotFoundExceptionTest() {
        NoSuchElementException e = new NoSuchElementException("test");
        ErrorResponse expectedResponse = new ErrorResponse(e.getMessage());
        ErrorResponse actualResponse = handler.handleAccessDeniedException(new AccessDeniedException("test"));
        assertEquals(actualResponse.getError(), expectedResponse.getError());
    }

    @Test
    void handleUserNotFoundExceptionTest() {
        NoSuchElementException e = new NoSuchElementException("test");
        ErrorResponse expectedResponse = new ErrorResponse(e.getMessage());
        ErrorResponse actualResponse = handler.handleAccessDeniedException(new AccessDeniedException("test"));
        assertEquals(actualResponse.getError(), expectedResponse.getError());
    }
}
