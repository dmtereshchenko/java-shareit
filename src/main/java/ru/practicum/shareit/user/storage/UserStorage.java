package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserStorage {

    /**
     * сохранение пользователя в хранилище
     */
    User create(User user);

    /**
     * проверка наличия пользователя с @param id в хранилище
     */
    boolean exists(int id);

    /**
     * получение всех пользователей из хранилища
     */
    List<User> getAll();

    /**
     * получение определенного пользователя с @param id из хранилища
     */
    User get(int id);

    /**
     * обновление данных определенного пользователя в хранилище
     */
    boolean update(User user);

    /**
     * удаление пользователя с @param id из хранилища
     */
    void delete(int id);
}
