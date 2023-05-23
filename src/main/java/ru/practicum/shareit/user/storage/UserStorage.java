package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserStorage {
    User create(User user);

    boolean exists(int id);

    List<User> getAll();

    User get(int id);

    boolean update(User user);

    void delete(int id);
}
