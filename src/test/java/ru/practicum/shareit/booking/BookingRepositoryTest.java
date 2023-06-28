package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.storage.BookingRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingRepositoryTest {

    @Mock
    private BookingRepository bookingRepository;
    private final Booking booking = new Booking(1L, null, null, null, null, null);
    List<Booking> bookings = List.of(booking);
    LocalDateTime now = LocalDateTime.now();

    @Test
    void findBookingsByBookerIdTest() {
        when(bookingRepository.findBookingsByBookerId(anyLong(), any(Pageable.class))).thenReturn(bookings);
        assertEquals(bookings, bookingRepository.findBookingsByBookerId(1L, Pageable.unpaged()));
    }

    @Test
    void findBookingsByBookerIdAndStartIsBeforeAndEndIsAfterTest() {
        when(bookingRepository.findBookingsByBookerIdAndStartIsBeforeAndEndIsAfter(anyLong(), any(LocalDateTime.class),
                any(LocalDateTime.class), any(Pageable.class))).thenReturn(bookings);
        assertEquals(bookings, bookingRepository.findBookingsByBookerIdAndStartIsBeforeAndEndIsAfter(1L, now, now, Pageable.unpaged()));
    }

    @Test
    void findBookingsByBookerIdAndEndIsBeforeTest() {
        when(bookingRepository.findBookingsByBookerIdAndEndIsBefore(anyLong(), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(bookings);
        assertEquals(bookings, bookingRepository.findBookingsByBookerIdAndEndIsBefore(1L, now, Pageable.unpaged()));
    }

    @Test
    void findBookingsByBookerIdAndStartIsAfterTest() {
        when(bookingRepository.findBookingsByBookerIdAndStartIsAfter(anyLong(), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(bookings);
        assertEquals(bookings, bookingRepository.findBookingsByBookerIdAndStartIsAfter(1L, now, Pageable.unpaged()));
    }

    @Test
    void findBookingsByBookerIdAndStatusTest() {
        when(bookingRepository.findBookingsByBookerIdAndStatus(anyLong(), any(Status.class), any(Pageable.class))).thenReturn(bookings);
        assertEquals(bookings, bookingRepository.findBookingsByBookerIdAndStatus(1L, Status.WAITING, Pageable.unpaged()));
    }

    @Test
    void findBookingsByItemOwnerIdTest() {
        when(bookingRepository.findBookingsByItemOwnerId(anyLong(), any(Pageable.class))).thenReturn(bookings);
        assertEquals(bookings, bookingRepository.findBookingsByItemOwnerId(1L, Pageable.unpaged()));
    }

    @Test
    void findBookingsByItemOwnerIdAndStartIsBeforeAndEndIsAfterTest() {
        when(bookingRepository.findBookingsByItemOwnerIdAndStartIsBeforeAndEndIsAfter(anyLong(), any(LocalDateTime.class),
                any(LocalDateTime.class), any(Pageable.class))).thenReturn(bookings);
        assertEquals(bookings, bookingRepository.findBookingsByItemOwnerIdAndStartIsBeforeAndEndIsAfter(1L, now, now, Pageable.unpaged()));
    }

    @Test
    void findBookingsByItemOwnerIdAndEndIsBeforeTest() {
        when(bookingRepository.findBookingsByItemOwnerIdAndEndIsBefore(anyLong(), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(bookings);
        assertEquals(bookings, bookingRepository.findBookingsByItemOwnerIdAndEndIsBefore(1L, now, Pageable.unpaged()));
    }

    @Test
    void findBookingsByItemOwnerIdAndStartIsAfterTest() {
        when(bookingRepository.findBookingsByItemOwnerIdAndStartIsAfter(anyLong(), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(bookings);
        assertEquals(bookings, bookingRepository.findBookingsByItemOwnerIdAndStartIsAfter(1L, now, Pageable.unpaged()));
    }

    @Test
    void findBookingsByItemOwnerIdAndStatusTest() {
        when(bookingRepository.findBookingsByItemOwnerIdAndStatus(anyLong(), any(Status.class), any(Pageable.class))).thenReturn(bookings);
        assertEquals(bookings, bookingRepository.findBookingsByItemOwnerIdAndStatus(1L, Status.WAITING, Pageable.unpaged()));
    }

    @Test
    void findBookingsByBookerIdAndItemIdAndEndIsBeforeAndStatusTest() {
        when(bookingRepository.findBookingsByBookerIdAndItemIdAndEndIsBeforeAndStatus(anyLong(), anyLong(), any(LocalDateTime.class),
                any(Status.class))).thenReturn(bookings);
        assertEquals(bookings, bookingRepository.findBookingsByBookerIdAndItemIdAndEndIsBeforeAndStatus(1L, 1L, now, Status.WAITING));
    }

    @Test
    void getPreviousAndNextBookingsTest() {
        when(bookingRepository.getPreviousAndNextBookings(anyList(), any(LocalDateTime.class))).thenReturn(bookings);
        assertEquals(bookings, bookingRepository.getPreviousAndNextBookings(new ArrayList<>(), now));
    }
}