package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class ItemMapper {

    public Item toItemCreate(ItemDto itemDto, User user) {
        Item item = new Item();
        item.setId(itemDto.getId() != 0 ? item.getId() : 0);
        item.setName(itemDto.getName() != null ? itemDto.getName() : null);
        item.setDescription(itemDto.getDescription() != null ? itemDto.getDescription() : null);
        item.setAvailable(itemDto.getAvailable());
        item.setOwner(user);
        return item;
    }

    public Item toItemUpdate(ItemDto itemDto, Item item) {
        item.setId(itemDto.getId() != 0 ? itemDto.getId() : item.getId());
        item.setName(itemDto.getName() != null ? itemDto.getName() : item.getName());
        item.setDescription(itemDto.getDescription() != null ? itemDto.getDescription() : item.getDescription());
        item.setAvailable(itemDto.getAvailable() != null ? itemDto.getAvailable() : item.getAvailable());
        return item;
    }
}
