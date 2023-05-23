package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Set;

public interface ItemService {

    Item create(ItemDto itemDto, int userId);

    Item update(ItemDto itemDto, int userId, int itemId);

    Item get(int id, int userId);

    List<Item> getAll(int userId);

    void delete(int id);

    Set<Item> find(String text);
}
