package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.InMemoryUserStorage;

import java.util.List;

@Service
public class InMemoryUserService implements UserService {

    private static final UserMapper mapper = new UserMapper();
    private final InMemoryUserStorage storage;

    @Autowired
    public InMemoryUserService(InMemoryUserStorage storage) {
        this.storage = storage;
    }

    @Override
    public User create(User user) {
        if (storage.validatorAdd(user.getEmail())) {
            return storage.create(user);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Пользователь с этим адресом электронной почты уже зарегистрирован.");
        }
    }

    @Override
    public User update(UserDto userDto, int id) {
        if (!exists(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с таким Id не найден");
        } else if (storage.validatorCheck(userDto.getEmail()) && !userDto.getEmail().equals(storage.get(id).getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Пользователь с этим адресом электронной почты уже существует.");
        } else {
            String email = storage.get(id).getEmail();
            storage.validatorDelete(email);
            User user = UserMapper.toUser(userDto, storage.get(id));
            if (!storage.update(user)) {
                storage.validatorAdd(email);
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Пользователь с этим адресом электронной почты уже существует.");
            }
            return user;
        }
    }

    @Override
    public User get(int id) {
        if (exists(id)) {
            return storage.get(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с этим идентификатором не найден.");
        }
    }

    @Override
    public List<User> getAll() {
        return storage.getAll();
    }

    @Override
    public void delete(int id) {
        storage.delete(id);
    }

    @Override
    public boolean exists(int id) {
        return storage.exists(id);
    }
}
