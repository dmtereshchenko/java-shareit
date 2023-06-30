package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDtoLong;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class ItemDtoLongTest {

    ItemDtoLong itemDtoLong = new ItemDtoLong(1L, "itemName1", "itemDescription1", true, null, null, new ArrayList<>());
    @Autowired
    private JacksonTester<ItemDtoLong> json;

    @Test
    void serializeTest() throws Exception {
        JsonContent<ItemDtoLong> result = json.write(itemDtoLong);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.name");
        assertThat(result).hasJsonPath("$.description");
        assertThat(result).hasJsonPath("$.available");
        assertThat(result).hasJsonPath("$.lastBooking");
        assertThat(result).hasJsonPath("$.nextBooking");
        assertThat(result).hasJsonPath("$.comments");
        assertThat(result).extractingJsonPathValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo(itemDtoLong.getName());
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(itemDtoLong.getDescription());
        assertThat(result).extractingJsonPathBooleanValue("$.available").isTrue();
        assertThat(result).extractingJsonPathValue("$.lastBooking").isNull();
        assertThat(result).extractingJsonPathValue("$.nextBooking").isNull();
        assertThat(result).extractingJsonPathValue("$.comments").isEqualTo(itemDtoLong.getComments());
    }

    @Test
    void addCommentTest() {
        CommentDto commentDto = new CommentDto(1L, "commentText", "authorName", LocalDateTime.now());
        ItemDtoLong itemDtoLong1 = itemDtoLong;
        itemDtoLong.addComment(commentDto);
        itemDtoLong1.setComments(List.of(commentDto));
        assertEquals(itemDtoLong1, itemDtoLong);
    }
}
