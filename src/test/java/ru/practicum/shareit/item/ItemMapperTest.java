package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoLong;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemMapperTest {

    private final Item item = new Item(1L, "itemName1", "itemDescription1", true, null, 1L);
    private final ItemDto itemDto1 = new ItemDto(1L, "itemName1", "itemDescription1", true, 1L);
    private final ItemDtoLong itemDtoLong1 = new ItemDtoLong(1L, "itemName1", "itemDescription1", true, null, null, new ArrayList<>());

    @Test
    void fromItemDtoToItemTest() {
        assertEquals(item, ItemMapper.toItem(itemDto1, null));
    }

    @Test
    void fromItemToItemDtoTest() {
        assertEquals(itemDto1, ItemMapper.toDto(item));
    }

    @Test
    void fromItemToItemDtoLongTest() {
        assertEquals(itemDtoLong1, ItemMapper.toDtoLong(item));
    }
}
