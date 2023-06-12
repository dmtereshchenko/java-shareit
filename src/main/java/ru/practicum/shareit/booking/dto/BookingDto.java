package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDto {
    private final long id;
    @NotNull
    private final long itemId;
    @FutureOrPresent
    @NotNull
    private final LocalDateTime start;
    @NotNull
    @Future
    private final LocalDateTime end;
    private final ItemDto item;
    private final User booker;
    private Status status;
}
