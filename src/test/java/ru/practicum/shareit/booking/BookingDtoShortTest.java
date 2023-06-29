package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.dto.BookingDtoShort;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.practicum.shareit.Constant.formatter;

@JsonTest
public class BookingDtoShortTest {

    @Autowired
    private JacksonTester<BookingDtoShort> json;

    @Test
    void serializeTest() throws Exception {
        BookingDtoShort bookingDtoShort = new BookingDtoShort(1L, 1L, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        JsonContent<BookingDtoShort> result = json.write(bookingDtoShort);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.bookerId");
        assertThat(result).hasJsonPath("$.start");
        assertThat(result).hasJsonPath("$.end");
        assertThat(result).extractingJsonPathValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathValue("$.bookerId").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo(bookingDtoShort.getStart().format(formatter));
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo(bookingDtoShort.getEnd().format(formatter));
    }
}
