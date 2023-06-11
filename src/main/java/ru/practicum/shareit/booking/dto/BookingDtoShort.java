package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDtoShort {

    private long id;
    private long bookerId;
    private LocalDateTime start;
    private LocalDateTime end;
}
