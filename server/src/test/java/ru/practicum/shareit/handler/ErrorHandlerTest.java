package ru.practicum.shareit.handler;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import ru.practicum.shareit.exceptions.*;

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
        ErrorResponse actualResponse = handler.handleActionNotFoundException(new ActionNotFoundException("test"));
        assertEquals(actualResponse.getError(), expectedResponse.getError());
    }

    @Test
    void handleBookingNotFoundExceptionTest() {
        NoSuchElementException e = new NoSuchElementException(("Бронирование с id" + 1L + " не найдено."));
        ErrorResponse expectedResponse = new ErrorResponse(e.getMessage());
        ErrorResponse actualResponse = handler.handleBookingNotFoundException(new BookingNotFoundException(1L));
        assertEquals(actualResponse.getError(), expectedResponse.getError());
    }

    @Test
    void handleInvalidDataExceptionTest() {
        ValidationException e = new ValidationException("test");
        ErrorResponse expectedResponse = new ErrorResponse(e.getMessage());
        ErrorResponse actualResponse = handler.handleInvalidDataException(new InvalidDataException("test"));
        assertEquals(actualResponse.getError(), expectedResponse.getError());
    }

    @Test
    void handleItemNotFoundExceptionTest() {
        NoSuchElementException e = new NoSuchElementException("Вещь с id" + 1L + " не найдена.");
        ErrorResponse expectedResponse = new ErrorResponse(e.getMessage());
        ErrorResponse actualResponse = handler.handleItemNotFoundException(new ItemNotFoundException(1L));
        assertEquals(actualResponse.getError(), expectedResponse.getError());
    }

    @Test
    void handleItemRequestNotFoundExceptionTest() {
        NoSuchElementException e = new NoSuchElementException("Запрос с id" + 1L + " не найден.");
        ErrorResponse expectedResponse = new ErrorResponse(e.getMessage());
        ErrorResponse actualResponse = handler.handleItemRequestNotFoundException(new ItemRequestNotFoundException(1L));
        assertEquals(actualResponse.getError(), expectedResponse.getError());
    }

    @Test
    void handleUserNotFoundExceptionTest() {
        NoSuchElementException e = new NoSuchElementException("Пользователь с Id " + 1L + " не найден");
        ErrorResponse expectedResponse = new ErrorResponse(e.getMessage());
        ErrorResponse actualResponse = handler.handleUserNotFoundException(new UserNotFoundException(1L));
        assertEquals(actualResponse.getError(), expectedResponse.getError());
    }

    @Test
    void handleThrowableTest() {
        Throwable t = new Throwable("test");
        ErrorResponse expectedResponse = new ErrorResponse(t.getMessage());
        ErrorResponse actualResponse = handler.handleThrowable(new Throwable("test"));
        assertEquals(actualResponse.getError(), expectedResponse.getError());
    }
}
