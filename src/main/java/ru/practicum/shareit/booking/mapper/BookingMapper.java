package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoShort;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

public class BookingMapper {

    public static Booking toBooking(BookingDto bookingDto, User user, Item item) {
        return new Booking(
                bookingDto.getId() != 0 ? bookingDto.getId() : 0,
                bookingDto.getStart() != null ? bookingDto.getStart() : null,
                bookingDto.getEnd() != null ? bookingDto.getEnd() : null,
                item,
                user,
                Status.WAITING
        );
    }

    public static BookingDto toDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getItem().getId(),
                booking.getStart(),
                booking.getEnd(),
                ItemMapper.toDto(booking.getItem()),
                UserMapper.toDto(booking.getBooker()),
                booking.getStatus()
        );
    }

    public static BookingDtoShort toBookingDtoShort(Booking booking) {
        return new BookingDtoShort(
                booking.getId(),
                booking.getBooker().getId(),
                booking.getStart(),
                booking.getEnd()
        );
    }
}
