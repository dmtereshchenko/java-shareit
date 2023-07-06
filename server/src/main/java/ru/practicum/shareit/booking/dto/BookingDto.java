package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDto {
    private final Long id;
    @NotNull
    private final Long itemId;
    @FutureOrPresent
    @NotNull
    private final LocalDateTime start;
    @NotNull
    @Future
    private final LocalDateTime end;
    private final ItemDto item;
    private final UserDto booker;
    private final Status status;
}