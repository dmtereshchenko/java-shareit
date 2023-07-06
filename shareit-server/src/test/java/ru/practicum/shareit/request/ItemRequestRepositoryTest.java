package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.storage.ItemRequestRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemRequestRepositoryTest {

    List<ItemRequest> requests = List.of(new ItemRequest(1L, "itemRequestDescription", 1L, LocalDateTime.now()));
    @Mock
    private ItemRequestRepository itemRequestRepository;

    @Test
    void findItemRequestByRequesterIdTest() {
        when(itemRequestRepository.findItemRequestByRequesterId(anyLong(), any(Sort.class))).thenReturn(requests);
        assertEquals(requests, itemRequestRepository.findItemRequestByRequesterId(1L, Sort.by(Sort.Direction.DESC, "created")));
    }

    @Test
    void findItemRequestsByRequesterIdNotOrderByCreatedDescTest() {
        when(itemRequestRepository.findItemRequestsByRequesterIdNotOrderByCreatedDesc(anyLong(), any(Pageable.class))).thenReturn(requests);
        assertEquals(requests, itemRequestRepository.findItemRequestsByRequesterIdNotOrderByCreatedDesc(1L, Pageable.unpaged()));
    }
}
