package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Component
public class InMemoryItemStorage implements ItemStorage {

    private final HashMap<Long, Item> storage = new HashMap<>();
    private final HashMap<Long, List<Item>> usersItems = new HashMap<>();
    private long itemId;

    @Override
    public Item create(Item item) {
        item.setId(generateId());
        storage.put(itemId, item);
        if (null == getAll(item.getOwner().getId())) {
            usersItems.put(item.getOwner().getId(), List.of(item));
        } else {
            List<Item> items = getAll(item.getOwner().getId());
            items.add(item);
            usersItems.put(item.getOwner().getId(), items);
        }
        return item;
    }

    @Override
    public boolean exists(long id) {
        return storage.containsKey(id);
    }

    @Override
    public List<Item> getAll(long userId) {
        if (usersItems.containsKey(userId)) {
            return usersItems.get(userId);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Item get(long id) {
        return storage.get(id);
    }

    @Override
    public void update(Item item) {
        storage.put(item.getId(), item);
        List<Item> items = usersItems.get(item.getOwner());
        for (Item item1 : items) {
            if (item1.getId() == item.getId()) {
                items.remove(item1);
                items.add(item);
                break;
            }
        }
        usersItems.put(item.getOwner().getId(), items);
    }

    @Override
    public void delete(long id) {
        storage.remove(id);
    }

    @Override
    public Set<Item> find(String text) {
        Set<Item> items = new HashSet<>();
        for (Item item : storage.values()) {
            if (item.getName().toLowerCase().contains(text.toLowerCase()) && item.getAvailable()) {
                items.add(item);
            }
            if (item.getDescription().toLowerCase().contains(text.toLowerCase()) && item.getAvailable()) {
                items.add(item);
            }
        }
        return items;
    }

    private long generateId() {
        return ++itemId;
    }
}
