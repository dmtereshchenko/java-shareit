package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.Constant;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exceptions.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public BookingDto create(BookingDto bookingDto, long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Item item = itemRepository.findById(bookingDto.getItemId()).orElseThrow(() -> new ItemNotFoundException(bookingDto.getItemId()));
        if (userId == item.getOwner().getId()) {
            throw new ActionNotFoundException("Нельзя взять вещь в аренду у самого себя.");
        } else if (!item.getAvailable()) {
            throw new AccessDeniedException("Вещь недоступна для брони.");
        } else if (bookingDto.getStart().isAfter(bookingDto.getEnd()) || bookingDto.getStart().isEqual(bookingDto.getEnd())) {
            throw new AccessDeniedException("Время окончания должно быть позже времени старта.");
        } else {
            return BookingMapper.toDto(bookingRepository.save(BookingMapper.toBooking(bookingDto, user, item)));
        }
    }

    @Override
    public BookingDto update(long bookingId, boolean approved, long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId));
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId);
        if (userId != booking.getItem().getOwner().getId() && userId != booking.getBooker().getId()) {
            throw new AccessDeniedException("Вы не являетесь владельцем вещи.");
        } else if (!Status.WAITING.equals(booking.getStatus())) {
            throw new AccessDeniedException("Невозможно изменить статус вещи еще раз.");
        } else if (!approved && userId == booking.getBooker().getId()) {
            booking.setStatus(Status.CANCELED);
        } else if (userId == booking.getItem().getOwner().getId()) {
            booking.setStatus(approved ? Status.APPROVED : Status.REJECTED);
        } else {
            throw new ActionNotFoundException("Невозможно выполнить действие.");
        }
        return BookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDto get(long bookingId, long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId));
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId);
        if (booking.getBooker().getId() != userId && booking.getItem().getOwner().getId() != userId)
            throw new ActionNotFoundException("Вы не являетесь автором бронирования, либо владельцем вещи.");
        return BookingMapper.toDto(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getAllByBooker(String state, long userId, int from, int size) throws InvalidDataException {
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId);
        LocalDateTime now = LocalDateTime.now();
        List<Booking> bookings;
        Pageable pageable = PageRequest.of(from / size, size, Constant.sortDesc);
        switch (state) {
            case "ALL":
                bookings = bookingRepository.findBookingsByBookerId(userId, pageable);
                break;
            case "CURRENT":
                bookings = bookingRepository.findBookingsByBookerIdAndStartIsBeforeAndEndIsAfter(userId, now,
                        now, pageable);
                break;
            case "PAST":
                bookings = bookingRepository.findBookingsByBookerIdAndEndIsBefore(userId, now, pageable);
                break;
            case "FUTURE":
                bookings = bookingRepository.findBookingsByBookerIdAndStartIsAfter(userId, now, pageable);
                break;
            case "WAITING":
                bookings = bookingRepository.findBookingsByBookerIdAndStatus(userId, Status.WAITING, pageable);
                break;
            case "REJECTED":
                bookings = bookingRepository.findBookingsByBookerIdAndStatus(userId, Status.REJECTED, pageable);
                break;
            default:
                throw new InvalidDataException("Unknown state: " + state);
        }
        return bookings.stream().map(BookingMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getAllByOwner(String state, long userId, int from, int size) throws InvalidDataException {
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId);
        LocalDateTime now = LocalDateTime.now();
        List<Booking> bookings;
        Pageable pageable = PageRequest.of(from / size, size, Constant.sortDesc);
        switch (state) {
            case "ALL":
                bookings = bookingRepository.findBookingsByItemOwnerId(userId, pageable);
                break;
            case "CURRENT":
                bookings = bookingRepository.findBookingsByItemOwnerIdAndStartIsBeforeAndEndIsAfter(userId, now,
                        now, pageable);
                break;
            case "PAST":
                bookings = bookingRepository.findBookingsByItemOwnerIdAndEndIsBefore(userId, now, pageable);
                break;
            case "FUTURE":
                bookings = bookingRepository.findBookingsByItemOwnerIdAndStartIsAfter(userId, now, pageable);
                break;
            case "WAITING":
                bookings = bookingRepository.findBookingsByItemOwnerIdAndStatus(userId, Status.WAITING, pageable);
                break;
            case "REJECTED":
                bookings = bookingRepository.findBookingsByItemOwnerIdAndStatus(userId, Status.REJECTED, pageable);
                break;
            default:
                throw new InvalidDataException("Unknown state: " + state);
        }
        return bookings.stream().map(BookingMapper::toDto).collect(Collectors.toList());
    }
}
