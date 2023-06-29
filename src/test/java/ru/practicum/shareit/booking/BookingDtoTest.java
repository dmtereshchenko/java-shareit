package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.practicum.shareit.Constant.formatter;

@JsonTest
public class BookingDtoTest {

    @Autowired
    private JacksonTester<BookingDto> json;

    @Test
    void serializeTest() throws Exception {
        UserDto user = new UserDto(1L, "userName", "userMail@test.test");
        ItemDto item = new ItemDto(1L, "itemName", "itemDescription", true, 1L);
        BookingDto bookingDto = new BookingDto(1L, item.getId(), LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2), item, user, Status.WAITING);
        JsonContent<BookingDto> result = json.write(bookingDto);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.itemId");
        assertThat(result).hasJsonPath("$.start");
        assertThat(result).hasJsonPath("$.end");
        assertThat(result).hasJsonPath("$.item.name");
        assertThat(result).hasJsonPath("$.booker.id");
        assertThat(result).hasJsonPath("$.booker.name");
        assertThat(result).extractingJsonPathValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathValue("$.itemId").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo(bookingDto.getStart().format(formatter));
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo(bookingDto.getEnd().format(formatter));
        assertThat(result).extractingJsonPathValue("$.booker.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.booker.name").isEqualTo(bookingDto.getBooker().getName());
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo(Status.WAITING.name());
    }
}
