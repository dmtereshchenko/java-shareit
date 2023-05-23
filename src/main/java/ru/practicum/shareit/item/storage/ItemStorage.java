package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Set;

public interface ItemStorage {

    Item create(Item item);

    boolean exists(int id);

    List<Item> getAll(int userId);

    Item get(int id);

    void update(Item item);

    Set<Item> find(String text);

    void delete(int id);
}
