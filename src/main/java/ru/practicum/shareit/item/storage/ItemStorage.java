package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Set;

public interface ItemStorage {

    /**
     * сохранение вещи в хранилище
     */
    Item create(Item item);

    /**
     * проверка наличия вещи с @param id в хранилище
     */
    boolean exists(long id);

    /**
     * получение всех вещей из хранилища
     */
    List<Item> getAll(long userId);

    /**
     * получение определенной вещи с @param id из хранилища
     */
    Item get(long id);

    /**
     * обновление данных определенной вещи в хранилище
     */
    void update(Item item);

    /**
     * поиск вещи в хранилище по ключевому слову
     */
    Set<Item> find(String text);

    /**
     * удаление вещи с @param id из хранилища
     */
    void delete(long id);
}
