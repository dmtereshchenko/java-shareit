package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemRepositoryTest {

    private final Item item = new Item(1L, "itemName1", "itemDescription1", true, null, 1L);
    private final List<Item> items = List.of(item);
    @Mock
    private ItemRepository itemRepository;

    @Test
    void findByOwnerIdTest() {
        when(itemRepository.findByOwnerId(anyLong(), any(Pageable.class))).thenReturn(items);
        assertEquals(items, itemRepository.findByOwnerId(1L, Pageable.unpaged()));
    }

    @Test
    void findAllByDescriptionContainingIgnoreCaseAndAvailableIsTrueTest() {
        when(itemRepository.findAllByDescriptionContainingIgnoreCaseAndAvailableIsTrue(anyString(), any(Pageable.class)))
                .thenReturn(items);
        assertEquals(items, itemRepository.findAllByDescriptionContainingIgnoreCaseAndAvailableIsTrue("text", Pageable.unpaged()));
    }

    @Test
    void findAllByRequestIdInOrderByIdDescTest() {
        when(itemRepository.findAllByRequestIdInOrderByIdDesc(anyList())).thenReturn(items);
        assertEquals(items, itemRepository.findAllByRequestIdInOrderByIdDesc(List.of(1L)));
    }
}
