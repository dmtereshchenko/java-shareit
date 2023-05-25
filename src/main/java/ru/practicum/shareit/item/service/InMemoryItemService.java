package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.InMemoryItemStorage;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class InMemoryItemService implements ItemService {

    private final InMemoryItemStorage storage;
    private final UserService userService;

    @Autowired
    public InMemoryItemService(InMemoryItemStorage storage, UserService userService) {
        this.storage = storage;
        this.userService = userService;
    }

    @Override
    public ItemDto create(ItemDto itemDto, int userId) {
        if (!userService.exists(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователя с таким id не существует.");
        } else if (null == itemDto.getAvailable()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Нельзя создать вещь с отсутствием доступности бронирования.");
        } else {
            return ItemMapper.toDto(storage.create(ItemMapper.toItemCreate(itemDto, userId)));
        }
    }

    @Override
    public ItemDto update(ItemDto itemDto, int userId, int itemId) {
        if (!userService.exists(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователя с таким id не существует.");
        } else if (!storage.exists(itemId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Вещи с таким id не существует.");
        } else if (userId != storage.get(itemId).getOwner()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Редактировать вещь может только ее владелец.");
        } else {
            itemDto.setId(itemId);
            Item item = ItemMapper.toItemUpdate(itemDto, storage.get(itemId));
            storage.update(item);
            return ItemMapper.toDto(item);
        }
    }

    @Override
    public ItemDto get(int id, int userId) {
        return ItemMapper.toDto(storage.get(id));
    }

    @Override
    public List<ItemDto> getAll(int userId) {
        List<ItemDto> dtos = new ArrayList<>();
        List<Item> items = storage.getAll(userId);
        for (Item item : items) {
            dtos.add(ItemMapper.toDto(item));
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
    public void delete(int id) {
        storage.delete(id);
    }
}
