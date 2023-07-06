package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoShort;
import ru.practicum.shareit.booking.dto.Status;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingMapperTest {

    private final UserDto user = new UserDto(1L, "userName", "userMail@test.test");
    private final ItemDto item = new ItemDto(1L, "itemName", "itemDescription", true, 1L);
    private final Booking booking = new Booking(1L, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2),
            ItemMapper.toItem(item, UserMapper.toUser(user)), UserMapper.toUser(user), Status.WAITING);
    private final BookingDto bookingDto = new BookingDto(1L, item.getId(), booking.getStart(), booking.getEnd(), item, user, Status.WAITING);
    private final BookingDtoShort bookingDtoShort = new BookingDtoShort(1L, user.getId(), booking.getStart(), booking.getEnd());

    @Test
    void fromBookingDtoToBookingTest() {
        assertEquals(booking, BookingMapper.toBooking(bookingDto, booking.getBooker(), booking.getItem()));
    }

    @Test
    void fromBookingToBookingDtoTest() {
        assertEquals(bookingDto, BookingMapper.toDto(booking));
    }

    @Test
    void fromBookingToBookingDtoShortTest() {
        assertEquals(bookingDtoShort, BookingMapper.toBookingDtoShort(booking));
    }
}
