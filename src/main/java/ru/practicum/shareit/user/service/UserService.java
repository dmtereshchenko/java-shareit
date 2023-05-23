package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {

    User create(User user);

    User update(UserDto userDto, int id);

    User get(int id);

    List<User> getAll();

    void delete(int id);

    boolean exists(int id);
}
