package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ConstantTest {

    @Test
    void getUserIdTest() {
        assertEquals("X-Sharer-User-Id", Constant.userId);
    }

    @Test
    void getSortDescTest() {
        assertEquals(Sort.by(Sort.Direction.DESC, "start"), Constant.sortDesc);
    }

    @Test
    void getFormatterTest() {
        assertEquals(DateTimeFormatter.ISO_LOCAL_DATE_TIME, Constant.formatter);
    }
}
