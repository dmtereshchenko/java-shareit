package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoLong;

import java.util.List;

public interface ItemService {

    ItemDto create(ItemDto itemDto, long userId);

    ItemDto update(ItemDto itemDto, long userId, long itemId);

    ItemDtoLong get(long itemId, long userId);

    List<ItemDtoLong> getAll(long userId);

    void delete(long itemId);

    List<ItemDto> find(String text);

    CommentDto addComment(CommentDto commentDto, long userId, long itemId);
}
