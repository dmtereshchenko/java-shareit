package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto, long userId);

    UserDto get(long userId);

    List<UserDto> getAll();

    void delete(long userId);

    boolean exists(long userId);
}
