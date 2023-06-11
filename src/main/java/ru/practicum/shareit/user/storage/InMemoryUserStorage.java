package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Long, User> storage = new HashMap<>();
    private final Set<String> emailUnicalizator = new HashSet<>();
    private long userId;

    @Override
    public User create(User user) {
        user.setId(generateId());
        storage.put(userId, user);
        return user;
    }

    @Override
    public boolean exists(long id) {
        return storage.containsKey(id);
    }

    @Override
    public List<User> getAll() {
        List<User> allUsers = new ArrayList<>();
        for (User user : storage.values()) {
            allUsers.add(user);
        }
        return allUsers;
    }

    @Override
    public User get(long id) {
        return storage.get(id);
    }

    @Override
    public boolean update(User user) {
        boolean success = validatorAdd(user.getEmail());
        if (success) {
            storage.put(user.getId(), user);
        }
        return success;
    }

    @Override
    public void delete(long id) {
        validatorDelete(get(id).getEmail());
        storage.remove(id);
    }

    public boolean validatorAdd(String email) {
        return emailUnicalizator.add(email);
    }

    public void validatorDelete(String email) {
        emailUnicalizator.remove(email);
    }

    public boolean validatorCheck(String email) {
        return emailUnicalizator.contains(email);
    }

    private long generateId() {
        return ++userId;
    }
}