package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoLong;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.InMemoryItemStorage;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class InMemoryItemService implements ItemService {

    private final InMemoryItemStorage storage;
    private final UserService userService;

    @Override
    public ItemDto create(ItemDto itemDto, long userId) {
        if (!userService.exists(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователя с таким id не существует.");
        } else if (null == itemDto.getAvailable()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Нельзя создать вещь с отсутствием доступности бронирования.");
        } else {
            return ItemMapper.toDto(ItemMapper.toItem(itemDto, UserMapper.toUser(userService.get(userId))));
        }
    }

    @Override
    public ItemDto update(ItemDto itemDto, long userId, long itemId) {
        if (!userService.exists(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователя с таким id не существует.");
        } else if (!storage.exists(itemId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Вещи с таким id не существует.");
        } else if (userId != storage.get(itemId).getOwner().getId()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Редактировать вещь может только ее владелец.");
        } else {
            Item item = ItemMapper.toItem(itemDto, UserMapper.toUser(userService.get(userId)));
            storage.update(item);
            return ItemMapper.toDto(item);
        }
    }

    @Override
    public ItemDtoLong get(long id, long userId) {
        return ItemMapper.toDtoLong(storage.get(id));
    }

    @Override
    public List<ItemDtoLong> getAll(long userId) {
        List<ItemDtoLong> dtos = new ArrayList<>();
        List<Item> items = storage.getAll(userId);
        for (Item item : items) {
            dtos.add(ItemMapper.toDtoLong(item));
        }
        return dtos;
    }

    @Override
    public List<ItemDto> find(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        } else {
            Set<Item> itemSet = storage.find(text);
            List<ItemDto> dtos = new ArrayList<>();
            for (Item item : itemSet) {
                dtos.add(ItemMapper.toDto(item));
            }
            return dtos;
        }
    }

    @Override
    public void delete(long id) {
        storage.delete(id);
    }

    @Override
    public CommentDto addComment(CommentDto commentDto, long userId, long itemId) {
        return null;
    }
}
