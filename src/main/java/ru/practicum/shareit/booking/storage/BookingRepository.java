package ru.practicum.shareit.booking.storage;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findBookingsByBookerId(Long bookerId, Sort sort);

    List<Booking> findBookingsByBookerIdAndStartIsBeforeAndEndIsAfter(Long bookerId, LocalDateTime start, LocalDateTime end, Sort sort);

    List<Booking> findBookingsByBookerIdAndEndIsBefore(Long bookerId, LocalDateTime end, Sort sort);

    List<Booking> findBookingsByBookerIdAndStartIsAfter(Long bookerId, LocalDateTime start, Sort sort);

    List<Booking> findBookingsByBookerIdAndStatus(Long bookerId, Status status, Sort sort);

    List<Booking> findBookingsByItemOwnerId(Long ownerId, Sort sort);

    List<Booking> findBookingsByItemOwnerIdAndStartIsBeforeAndEndIsAfter(Long ownerId, LocalDateTime start, LocalDateTime end, Sort sort);

    List<Booking> findBookingsByItemOwnerIdAndEndIsBefore(Long ownerId, LocalDateTime end, Sort sort);

    List<Booking> findBookingsByItemOwnerIdAndStartIsAfter(Long ownerId, LocalDateTime start, Sort sort);

    List<Booking> findBookingsByItemOwnerIdAndStatus(Long ownerId, Status status, Sort sort);

    List<Booking> findBookingsByBookerIdAndItemIdAndEndIsBeforeAndStatus(long bookerId, long itemId, LocalDateTime localDateTime, Status status);

    @Query(value = "SELECT * FROM bookings b " +
            "WHERE item_id IN (:itemId) " +
            "AND status = 'APPROVED' " +
            "AND ((start_booking = " +
            "(SELECT MAX(start_booking) " +
            "FROM bookings b " +
            "WHERE item_id IN (:itemId) " +
            "AND start_booking <= :now " +
            "AND status = 'APPROVED'" +
            "AND b.item_id = item_id)) " +
            "OR (b.start_booking = " +
            "(SELECT MIN(start_booking) " +
            "FROM bookings " +
            "WHERE item_id IN (:itemId) " +
            "AND start_booking >= :now " +
            "AND status = 'APPROVED' " +
            "AND b.item_id = item_id)))",
            nativeQuery = true)
    List<Booking> getPreviousAndNextBookings(@Param("itemId") List<Long> itemId, @Param("now") LocalDateTime now);

}
