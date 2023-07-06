package ru.practicum.shareit.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService service;

    @PostMapping()
    UserDto create(@Valid @RequestBody UserDto userDto) {
        return service.create(userDto);
    }

    @PatchMapping(value = "/{id}")
    UserDto update(@RequestBody UserDto userDto,
                   @PathVariable long id) {
        return service.update(userDto, id);
    }

    @GetMapping(value = "/{id}")
    UserDto get(@PathVariable long id) {
        return service.get(id);
    }

    @GetMapping()
    List<UserDto> getAll() {
        return service.getAll();
    }

    @DeleteMapping(value = "/{id}")
    void delete(@PathVariable long id) {
        service.delete(id);
    }
}
