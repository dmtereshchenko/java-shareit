package ru.practicum.shareit.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping(value = "/users")
    UserDto create(@Valid @RequestBody User user, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.create(user);
    }

    @PatchMapping(value = "/users/{id}")
    UserDto update(@Valid @RequestBody UserDto userDto, @PathVariable int id, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        return service.update(userDto, id);
    }

    @GetMapping(value = "/users/{id}")
    UserDto get(@PathVariable int id) {
        return service.get(id);
    }

    @GetMapping(value = "/users")
    List<UserDto> getAll() {
        return service.getAll();
    }

    @DeleteMapping(value = "/users/{id}")
    void delete(@PathVariable int id, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: {}, Строка параметров запроса: {}", request.getRequestURI(), request.getQueryString());
        service.delete(id);
    }
}
