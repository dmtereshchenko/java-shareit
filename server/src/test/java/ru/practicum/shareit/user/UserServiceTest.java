package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exceptions.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserServiceImpl;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final UserDto userDto = new UserDto(1L, "userName", "userMail@test.test");
    private final User user = new User(1L, "userName", "userMail@test.test");
    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserServiceImpl service;

    @Test
    void createUserTest() {
        when(repository.save(any())).thenReturn(user);
        assertEquals(userDto, service.create(userDto));
        verify(repository).save(any());
    }

    @Test
    void updateUserTest() {
        user.setName("userUpdateName1");
        user.setEmail("userUpdateMail1");
        when(repository.save(any())).thenReturn(user);
        when(repository.findById(anyLong())).thenReturn(Optional.of(user));
        assertEquals(userDto, service.update(userDto, userDto.getId()));
    }

    @Test
    void updateInExistUserTest() {
        assertThrows(UserNotFoundException.class, () -> service.update(userDto, user.getId()));
    }

    @Test
    void getUserTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(user));
        assertEquals(userDto, service.get(user.getId()));
    }

    @Test
    void throwsUserNotFoundExceptionTest() {
        assertThrows(UserNotFoundException.class, () -> service.get(userDto.getId()));
    }

    @Test
    void getAllTest() {
        when(service.getAll()).thenReturn(new ArrayList<>());
        assertEquals(service.getAll(), new ArrayList<>());
    }

    @Test
    void deleteUserTest() {
        service.delete(userDto.getId());
        verify(repository).deleteById(anyLong());
    }

    @Test
    void existsTest() {
        when(repository.existsById(anyLong())).thenReturn(true);
        assertTrue(service.exists(user.getId()));
    }
}
