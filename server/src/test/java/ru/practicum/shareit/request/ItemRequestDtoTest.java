package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.practicum.shareit.Constant.formatter;

@JsonTest
public class ItemRequestDtoTest {

    ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "itemRequestDescription", LocalDateTime.now(), new ArrayList<>());
    @Autowired
    private JacksonTester<ItemRequestDto> json;

    @Test
    void serializeTest() throws Exception {
        JsonContent<ItemRequestDto> result = json.write(itemRequestDto);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.description");
        assertThat(result).hasJsonPath("$.created");
        assertThat(result).hasJsonPath("$.items");
        assertThat(result).extractingJsonPathValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(itemRequestDto.getDescription());
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo(itemRequestDto.getCreated().format(formatter));
        assertThat(result).extractingJsonPathValue("$.items").isEqualTo(itemRequestDto.getItems());
    }

    @Test
    void addItemDtoTest() {
        ItemDto itemDto = new ItemDto(1L, "itemName", "itemDescription", true, itemRequestDto.getId());
        ItemRequestDto itemRequestDto1 = itemRequestDto;
        itemRequestDto.addItemDto(itemDto);
        itemRequestDto1.setItems(List.of(itemDto));
        assertEquals(itemRequestDto1, itemRequestDto);
    }
}
