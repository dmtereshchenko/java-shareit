package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserClient client;

    @PostMapping
    ResponseEntity<Object> create(@Valid @RequestBody UserDto userDto) {
        log.info("Получен запрос POST /users/");
        return client.create(userDto);
    }

    @PatchMapping(value = "/{userId}")
    ResponseEntity<Object> update(@RequestBody UserDto userDto, @PathVariable long userId) {
        log.info("Получен запрос PATCH /users/{}/", userId);
        client.update(userDto, userId);
        return client.update(userDto, userId);
    }

    @GetMapping(value = "/{userId}")
    ResponseEntity<Object> get(@PathVariable long userId) {
        log.info("Получен запрос GET /users/{}/", userId);
        return client.get(userId);
    }

    @GetMapping
    ResponseEntity<Object> getAll() {
        log.info("Получен запрос GET /users/");
        return client.getAll();
    }

    @DeleteMapping(value = "/{userId}")
    ResponseEntity<Object> delete(@PathVariable long userId) {
        log.info("Получен запрос DELETE /users/{}/", userId);
        return client.delete(userId);
    }
}
