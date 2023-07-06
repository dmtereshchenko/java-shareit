package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingDtoShort;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemDtoLong {

    private final Long id;
    private final String name;
    private final String description;
    private final Boolean available;
    private BookingDtoShort lastBooking;
    private BookingDtoShort nextBooking;
    private List<CommentDto> comments;

    public void addComment(CommentDto commentDto) {
        comments.add(commentDto);
    }
}
