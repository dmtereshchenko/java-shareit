package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private final User user = new User(1L, "userName", "userMail@test.test");
    private final UserDto userDto = new UserDto(1L, "userName", "userMail@test.test");

    @Test
    void fromUserDtoToUserTest() {
        assertEquals(user, UserMapper.toUser(userDto));
    }

    @Test
    void fromUserToUserDtoTest() {
        assertEquals(userDto, UserMapper.toDto(user));
    }
}
