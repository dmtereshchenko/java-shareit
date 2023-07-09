package ru.practicum.shareit.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService service;

    @PostMapping
    UserDto create(@RequestBody UserDto userDto) {
        log.info("Получен запрос POST /users/");
        return service.create(userDto);
    }

    @PatchMapping(value = "/{userId}")
    UserDto update(@RequestBody UserDto userDto,
                   @PathVariable long userId) {
        log.info("Получен запрос PATCH /users/{}/", userId);
        return service.update(userDto, userId);
    }

    @GetMapping(value = "/{userId}")
    UserDto get(@PathVariable long userId) {
        log.info("Получен запрос GET /users/{}/", userId);
        return service.get(userId);
    }

    @GetMapping
    List<UserDto> getAll() {
        log.info("Получен запрос GET /users/");
        return service.getAll();
    }

    @DeleteMapping(value = "/{userId}")
    void delete(@PathVariable long userId) {
        log.info("Получен запрос DELETE /users/{}/", userId);
        service.delete(userId);
    }
}
