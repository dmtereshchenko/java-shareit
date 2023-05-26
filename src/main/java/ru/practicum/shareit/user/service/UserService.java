package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {

    UserDto create(User user);

    UserDto update(UserDto userDto, int id);

    UserDto get(int id);

    List<UserDto> getAll();

    void delete(int id);

    boolean exists(int id);
}
