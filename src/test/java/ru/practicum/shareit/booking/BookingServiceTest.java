package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exceptions.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    private final User owner = new User(1L, "userName", "userMail@test.test");
    private final User booker = new User(2L, "userName2", "userMail2@test.test");
    private final Item item = new Item(1L, "itemName", "itemDescription", true, owner, 1L);
    private final Booking booking = new Booking(1L, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2),
            item, booker, Status.WAITING);
    private final BookingDto bookingDto = BookingMapper.toDto(booking);
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookingRepository bookingRepository;
    @InjectMocks
    private BookingServiceImpl service;

    @Test
    void createBookingTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(booker));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(bookingRepository.save(any())).thenReturn(booking);
        assertEquals(bookingDto, service.create(bookingDto, booker.getId()));
        verify(bookingRepository).save(any());
    }

    @Test
    void createBookingThrowsUserNotFoundExceptionTest() {
        assertThrows(UserNotFoundException.class, () -> service.create(bookingDto, booker.getId()));
    }

    @Test
    void createBookingThrowsItemNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(booker));
        assertThrows(ItemNotFoundException.class, () -> service.create(bookingDto, booker.getId()));
    }

    @Test
    void createBookingByOwnerTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(owner));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        assertThrows(ActionNotFoundException.class, () -> service.create(bookingDto, owner.getId()));
    }

    @Test
    void createBookingWithItemUnavailableTest() {
        Item item1 = new Item(1L, "itemName", "itemDescription", false, owner, 1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(booker));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item1));
        assertThrows(AccessDeniedException.class, () -> service.create(bookingDto, booker.getId()));
    }

    @Test
    void createBookingWithWrongStartEndTest() {
        BookingDto bookingDto1 = new BookingDto(2L, 1L, LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(1),
                null, null, Status.WAITING);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(booker));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        assertThrows(AccessDeniedException.class, () -> service.create(bookingDto1, booker.getId()));
    }

    @Test
    void updateBookingByOwnerSetApprovedTest() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any())).thenReturn(booking);
        Booking booking1 = new Booking(1L, booking.getStart(), booking.getEnd(), item, booker, Status.APPROVED);
        assertEquals(BookingMapper.toDto(booking1), service.update(booking.getId(), true, owner.getId()));
        verify(bookingRepository).save(any());
    }

    @Test
    void updateBookingByBookerSetCancelledTest() {
        Booking booking1 = new Booking(1L, booking.getStart(), booking.getEnd(), item, owner, Status.CANCELED);
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any())).thenReturn(booking1);
        assertEquals(BookingMapper.toDto(booking1), service.update(booking.getId(), false, owner.getId()));
    }

    @Test
    void updateBookingWithWrongUserTest() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        assertThrows(AccessDeniedException.class, () -> service.update(booking.getId(), true, 3L));
    }

    @Test
    void updateBookingAlreadyUpdatedTest() {
        Booking booking1 = new Booking(1L, booking.getStart(), booking.getEnd(), item, owner, Status.APPROVED);
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking1));
        assertThrows(AccessDeniedException.class, () -> service.update(booking1.getId(), false, owner.getId()));
    }

    @Test
    void updateBookingApprovedByBookerTest() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        assertThrows(ActionNotFoundException.class, () -> service.update(booking.getId(), true, booker.getId()));
    }

    @Test
    void getBookingTest() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        assertEquals(bookingDto, service.get(booking.getId(), owner.getId()));
    }

    @Test
    void getBookingByInExistTest() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        assertThrows(UserNotFoundException.class, () -> service.get(booking.getId(), 3L));
    }

    @Test
    void getBookingWrongUserTest() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        assertThrows(ActionNotFoundException.class, () -> service.get(booking.getId(), 3L));
    }

    @Test
    void getWrongBookingTest() {
        assertThrows(BookingNotFoundException.class, () -> service.get(3L, owner.getId()));
    }

    @Test
    void getAllByBookerTest() throws InvalidDataException {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findBookingsByBookerId(anyLong(), any(Pageable.class))).thenReturn(List.of(booking));
        assertEquals(List.of(bookingDto), service.getAllByBooker("ALL", booker.getId(), 0, 10));
    }

    @Test
    void getCurrentByBookerTest() throws InvalidDataException {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findBookingsByBookerIdAndStartIsBeforeAndEndIsAfter(anyLong(), any(LocalDateTime.class),
                any(LocalDateTime.class), any(Pageable.class))).thenReturn(List.of(booking));
        assertEquals(List.of(bookingDto), service.getAllByBooker("CURRENT", booker.getId(), 0, 10));
    }

    @Test
    void getPastByBookerTest() throws InvalidDataException {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findBookingsByBookerIdAndEndIsBefore(anyLong(), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(List.of(booking));
        assertEquals(List.of(bookingDto), service.getAllByBooker("PAST", booker.getId(), 0, 10));
    }

    @Test
    void getFutureByBookerTest() throws InvalidDataException {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findBookingsByBookerIdAndStartIsAfter(anyLong(), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(List.of(booking));
        assertEquals(List.of(bookingDto), service.getAllByBooker("FUTURE", booker.getId(), 0, 10));
    }

    @Test
    void getWaitingByBookerTest() throws InvalidDataException {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findBookingsByBookerIdAndStatus(anyLong(), any(Status.class), any(Pageable.class))).thenReturn(List.of(booking));
        assertEquals(List.of(bookingDto), service.getAllByBooker("WAITING", booker.getId(), 0, 10));
    }

    @Test
    void getRejectedByBookerTest() throws InvalidDataException {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findBookingsByBookerIdAndStatus(anyLong(), any(Status.class), any(Pageable.class))).thenReturn(List.of(booking));
        assertEquals(List.of(bookingDto), service.getAllByBooker("REJECTED", booker.getId(), 0, 10));
    }

    @Test
    void getInvalidStateByBookerTest() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        assertThrows(InvalidDataException.class, () -> service.getAllByBooker("UNKNOWN STATE", booker.getId(), 0, 10));
    }

    @Test
    void getAllByBookerInExistTest() {
        assertThrows(UserNotFoundException.class, () -> service.getAllByBooker("ALL", 3L, 0, 10));
    }

    @Test
    void getAllByOwnerTest() throws InvalidDataException {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findBookingsByItemOwnerId(anyLong(), any(Pageable.class))).thenReturn(List.of(booking));
        assertEquals(List.of(bookingDto), service.getAllByOwner("ALL", owner.getId(), 0, 10));
    }

    @Test
    void getCurrentByOwnerTest() throws InvalidDataException {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findBookingsByItemOwnerIdAndStartIsBeforeAndEndIsAfter(anyLong(), any(LocalDateTime.class),
                any(LocalDateTime.class), any(Pageable.class))).thenReturn(List.of(booking));
        assertEquals(List.of(bookingDto), service.getAllByOwner("CURRENT", owner.getId(), 0, 10));
    }

    @Test
    void getPastByOwnerTest() throws InvalidDataException {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findBookingsByItemOwnerIdAndEndIsBefore(anyLong(), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(List.of(booking));
        assertEquals(List.of(bookingDto), service.getAllByOwner("PAST", owner.getId(), 0, 10));
    }

    @Test
    void getFutureByOwnerTest() throws InvalidDataException {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findBookingsByItemOwnerIdAndStartIsAfter(anyLong(), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(List.of(booking));
        assertEquals(List.of(bookingDto), service.getAllByOwner("FUTURE", owner.getId(), 0, 10));
    }

    @Test
    void getWaitingByOwnerTest() throws InvalidDataException {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findBookingsByItemOwnerIdAndStatus(anyLong(), any(Status.class), any(Pageable.class))).thenReturn(List.of(booking));
        assertEquals(List.of(bookingDto), service.getAllByOwner("WAITING", owner.getId(), 0, 10));
    }

    @Test
    void getRejectedByOwnerTest() throws InvalidDataException {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(bookingRepository.findBookingsByItemOwnerIdAndStatus(anyLong(), any(Status.class), any(Pageable.class))).thenReturn(List.of(booking));
        assertEquals(List.of(bookingDto), service.getAllByOwner("REJECTED", owner.getId(), 0, 10));
    }

    @Test
    void getInvalidStateByOwnerTest() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        assertThrows(InvalidDataException.class, () -> service.getAllByOwner("UNKNOWN STATE", booker.getId(), 0, 10));
    }

    @Test
    void getAllByOwnerInExistTest() {
        assertThrows(UserNotFoundException.class, () -> service.getAllByOwner("ALL", 3L, 0, 10));
    }
}
