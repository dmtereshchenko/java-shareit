package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemRequestMapperTest {

    private final ItemRequest itemRequest = new ItemRequest(1L, "itemRequestDescription", 1L, LocalDateTime.now());
    private final ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "itemRequestDescription", itemRequest.getCreated(), new ArrayList<>());

    @Test
    void fromItemRequestDtoToItemRequestTest() {
        assertEquals(itemRequest, ItemRequestMapper.toItemRequest(itemRequestDto, new User(1L, "userName", "userMail@test.test")));
    }

    @Test
    void fromItemRequestToItemRequestDtoTest() {
        assertEquals(itemRequestDto, ItemRequestMapper.toDto(itemRequest));
    }
}
