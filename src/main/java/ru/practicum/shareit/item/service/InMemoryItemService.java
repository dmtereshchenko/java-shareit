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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class InMemoryItemService implements ItemService {

    private static final ItemMapper mapper = new ItemMapper();
    private final InMemoryItemStorage storage;
    private final UserService userService;

    @Autowired
    public InMemoryItemService(InMemoryItemStorage storage, UserService userService) {
        this.storage = storage;
        this.userService = userService;
    }

    @Override
    public Item create(ItemDto itemDto, int userId) {
        if (!userService.exists(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователя с таким id не существует.");
        } else if (null == itemDto.getAvailable()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Нельзя создать вещь с отсутствием доступности бронирования.");
        } else {
            return storage.create(mapper.toItemCreate(itemDto, userService.get(userId)));
        }
    }

    @Override
    public Item update(ItemDto itemDto, int userId, int itemId) {
        if (!userService.exists(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователя с таким id не существует.");
        } else if (!storage.exists(itemId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Вещи с таким id не существует.");
        } else if (userId != storage.get(itemId).getOwner().getId()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Редактировать вещь может только ее владелец.");
        } else {
            itemDto.setId(itemId);
            Item item = mapper.toItemUpdate(itemDto, storage.get(itemId));
            storage.update(item);
            return item;
        }
    }

    @Override
    public Item get(int id, int userId) {
        return storage.get(id);
    }

    @Override
    public List<Item> getAll(int userId) {
        return storage.getAll(userId);
    }

    @Override
    public Set<Item> find(String text) {
        if (text.isBlank()) {
            return new HashSet<>();
        } else {
            return storage.find(text);
        }
    }

    @Override
    public void delete(int id) {
        storage.delete(id);
    }
}
