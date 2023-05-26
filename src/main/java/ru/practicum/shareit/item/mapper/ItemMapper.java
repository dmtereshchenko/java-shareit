package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public class ItemMapper {

    public static Item toItemCreate(ItemDto itemDto, int userId) {
        Item item = new Item();
        item.setId(itemDto.getId() != 0 ? item.getId() : 0);
        item.setName(itemDto.getName() != null ? itemDto.getName() : null);
        item.setDescription(itemDto.getDescription() != null ? itemDto.getDescription() : null);
        item.setAvailable(itemDto.getAvailable());
        item.setOwner(userId);
        return item;
    }

    public static Item toItemUpdate(ItemDto itemDto, Item item) {
        item.setId(itemDto.getId() != 0 ? itemDto.getId() : item.getId());
        item.setName(itemDto.getName() != null ? itemDto.getName() : item.getName());
        item.setDescription(itemDto.getDescription() != null ? itemDto.getDescription() : item.getDescription());
        item.setAvailable(itemDto.getAvailable() != null ? itemDto.getAvailable() : item.getAvailable());
        return item;
    }

    public static ItemDto toDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setRequestId(item.getRequest() != null ? item.getRequest().getId() : 0);
        return itemDto;
    }
}
