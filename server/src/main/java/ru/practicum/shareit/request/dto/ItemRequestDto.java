package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ItemRequestDto {

    private final Long id;
    private final String description;
    private final LocalDateTime created;
    private List<ItemDto> items;

    public void addItemDto(ItemDto itemDto) {
        items.add(itemDto);
    }
}
