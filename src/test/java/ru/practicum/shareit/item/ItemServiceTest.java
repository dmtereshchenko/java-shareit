package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exceptions.AccessDeniedException;
import ru.practicum.shareit.exceptions.ItemNotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoLong;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.item.storage.CommentRepository;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BookingRepository bookingRepository;
    @InjectMocks
    private ItemServiceImpl service;

    User user = new User(1L, "userName", "userMail@test.test");
    private final Item item = new Item(1L, "itemName1", "itemDescription1", true, user, 1L);
    private final ItemDto itemDto1 = new ItemDto(1L, "itemName1", "itemDescription1", true, 1L);
    private final ItemDtoLong itemDtoLong1 = new ItemDtoLong(1L, "itemName1", "itemDescription1", true, null, null, new ArrayList<>());

    @Test
    void createItemTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRepository.save(any())).thenReturn(item);
        assertEquals(itemDto1, service.create(itemDto1, user.getId()));
        verify(itemRepository).save(any());
    }

    @Test
    void updateItemTest() {
        item.setName("updateName");
        item.setDescription("updateDescription");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(itemRepository.save(any())).thenReturn(item);
        assertEquals(ItemMapper.toDto(item), service.update(ItemMapper.toDto(item), item.getOwner().getId(), item.getId()));
    }

    @Test
    void throwsAccessDeniedExceptionTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        assertThrows(AccessDeniedException.class, () -> service.update(itemDto1, 2L, item.getId()));
    }

    @Test
    void getItemTest() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        assertEquals(itemDtoLong1, service.get(item.getId(), user.getId()));
    }

    @Test
    void throwsItemNotFoundExceptionTest() {
        when(itemRepository.findById(anyLong())).thenThrow(new ItemNotFoundException(item.getId()));
        assertThrows(ItemNotFoundException.class, () -> service.get(item.getId(), user.getId()));
    }

    @Test
    void getAllTest() {
        when(itemRepository.findByOwnerId(anyLong(), any(Pageable.class))).thenReturn(List.of(item));
        when(commentRepository.findCommentsByItemId(anyList())).thenReturn(new ArrayList<>());
        assertNotNull(service.getAll(user.getId(), 0, 10));
    }

    @Test
    void findItemsByTextTest() {
        when(itemRepository.findAllByDescriptionContainingIgnoreCaseAndAvailableIsTrue(anyString(), any(Pageable.class)))
                .thenReturn(new ArrayList<>());
        assertNotNull(service.find("text", 0, 10));
        assertTrue(service.find("text", 0, 10).isEmpty());
    }

    @Test
    void addCommentTest() {
        CommentDto commentDto = new CommentDto(1L, "text", user.getName(), LocalDateTime.now());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(commentRepository.save(any())).thenReturn(CommentMapper.toComment(commentDto, user, item));
        when(bookingRepository.findBookingsByBookerIdAndItemIdAndEndIsBeforeAndStatus(anyLong(), anyLong(), any(LocalDateTime.class), any(Status.class)))
                .thenReturn(List.of(new Booking()));
        assertEquals(commentDto, service.addComment(commentDto, user.getId(), item.getId()));
    }
}
