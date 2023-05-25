package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto create(ItemDto itemDto, int userId);

    ItemDto update(ItemDto itemDto, int userId, int itemId);

    ItemDto get(int id, int userId);

    List<ItemDto> getAll(int userId);

    void delete(int id);

    List<ItemDto> find(String text);
}
