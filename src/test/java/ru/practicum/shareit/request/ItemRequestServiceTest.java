package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.exceptions.ItemRequestNotFoundException;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestServiceImpl;
import ru.practicum.shareit.request.storage.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemRequestServiceTest {

    private final User user = new User(1L, "userName", "userMail@test.test");
    private final ItemRequest itemRequest = new ItemRequest(1L, "itemRequestDescription", user.getId(), LocalDateTime.now());
    private final ItemRequestDto itemRequestDto = ItemRequestMapper.toDto(itemRequest);
    @InjectMocks
    ItemRequestServiceImpl service;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ItemRequestRepository itemRequestRepository;

    @Test
    void createItemRequestDtoTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRequestRepository.save(any())).thenReturn(itemRequest);
        assertEquals(itemRequestDto, service.create(itemRequestDto, user.getId()));
        verify(itemRequestRepository).save(any());
    }

    @Test
    void getItemRequestTest() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(itemRequestRepository.findById(anyLong())).thenReturn(Optional.of(itemRequest));
        assertEquals(itemRequestDto, service.get(user.getId(), itemRequest.getId()));
    }

    @Test
    void throwsItemRequestNotFoundExceptionTest() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        assertThrows(ItemRequestNotFoundException.class, () -> service.get(user.getId(), itemRequest.getId()));
    }

    @Test
    void getAllTest() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(itemRequestRepository.findItemRequestsByRequesterIdNotOrderByCreatedDesc(anyLong(), any(Pageable.class)))
                .thenReturn(List.of(itemRequest));
        assertEquals(List.of(itemRequestDto), service.getAll(user.getId(), 0, 10));
    }

    @Test
    void getAllByOwner() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(itemRequestRepository.findItemRequestByRequesterId(anyLong(), any(Sort.class))).thenReturn(List.of(itemRequest));
        assertEquals(List.of(itemRequestDto), service.getAllByOwner(user.getId()));
    }
}
