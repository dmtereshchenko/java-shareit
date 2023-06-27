package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.Constant;
import ru.practicum.shareit.item.controller.ItemController;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoLong;
import ru.practicum.shareit.item.service.ItemService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemControllerTest {

    @MockBean
    private ItemService service;
    private final ObjectMapper mapper;
    private final MockMvc mockMvc;
    private final ItemDto itemDto1 = new ItemDto(1L, "itemName1", "itemDescription1", true, 1L);
    private final ItemDto itemDto2 = new ItemDto(2L, "itemName2", "itemDescription2", true, 2L);

    private final ItemDtoLong itemDtoLong1 = new ItemDtoLong(1L, "itemName1", "itemDescription1", true, null, null, null);
    private final ItemDtoLong itemDtoLong2 = new ItemDtoLong(2L, "itemName2", "itemDescription2", true, null, null, null);

    @Test
    void createItemTest() throws Exception {
        when(service.create(any(), anyLong())).thenReturn(itemDto1);
        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(Constant.userId, 1L)
                        .content(mapper.writeValueAsString(itemDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemDto1.getId()))
                .andExpect(jsonPath("$.name").value(itemDto1.getName()))
                .andExpect(jsonPath("$.description").value(itemDto1.getDescription()))
                .andExpect(jsonPath("$.available").value(itemDto1.getAvailable()))
                .andExpect(jsonPath("$.requestId").value(itemDto1.getRequestId()));

    }

    @Test
    void createEmptyItemNameTest() throws Exception {
        ItemDto itemDto3 = new ItemDto(3L, "", "itemDescription3", true, 3L);
        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(Constant.userId, 1L)
                        .content(mapper.writeValueAsString(itemDto3)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createEmptyDescriptionTest() throws Exception {
        ItemDto itemDto3 = new ItemDto(3L, "itemName3", "", true, 3L);
        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(Constant.userId, 1L)
                        .content(mapper.writeValueAsString(itemDto3)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createEmptyAvailableTest() throws Exception {
        ItemDto itemDto3 = new ItemDto(3L, "itemName3", "itemDescription3", null, 3L);
        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(Constant.userId, 1L)
                        .content(mapper.writeValueAsString(itemDto3)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateItemTest() throws Exception {
        ItemDto itemDto3 = new ItemDto(1L, "itemNameUpdate", "itemDescriptionUpdate", true, 1L);
        when(service.update(any(), anyLong(), anyLong())).thenReturn(itemDto3);
        mockMvc.perform(patch("/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(Constant.userId, 1L)
                        .content(mapper.writeValueAsString(itemDto3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemDto3.getId()))
                .andExpect(jsonPath("$.name").value(itemDto3.getName()))
                .andExpect(jsonPath("$.description").value(itemDto3.getDescription()))
                .andExpect(jsonPath("$.available").value(true))
                .andExpect(jsonPath("$.requestId").value(itemDto3.getRequestId()));
    }

    @Test
    void getItemTest() throws Exception {
        when(service.get(anyLong(), anyLong())).thenReturn(itemDtoLong1);
        mockMvc.perform(get("/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(Constant.userId, 1L)
                        .content(mapper.writeValueAsString(itemDtoLong1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemDtoLong1.getId()))
                .andExpect(jsonPath("$.name").value(itemDtoLong1.getName()))
                .andExpect(jsonPath("$.description").value(itemDtoLong1.getDescription()))
                .andExpect(jsonPath("$.available").value(itemDtoLong1.getAvailable()));
    }

    @Test
    void getAllItemsTest() throws Exception {
        List<ItemDtoLong> items = List.of(itemDtoLong1, itemDtoLong2);
        when(service.getAll(anyLong(), anyInt(), anyInt())).thenReturn(items);
        mockMvc.perform(get("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(Constant.userId, 1L)
                        .content(mapper.writeValueAsString(itemDtoLong1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(itemDtoLong1.getId()))
                .andExpect(jsonPath("$[0].name").value(itemDtoLong1.getName()))
                .andExpect(jsonPath("$[0].description").value(itemDtoLong1.getDescription()))
                .andExpect(jsonPath("$[0].available").value(itemDtoLong1.getAvailable()))
                .andExpect(jsonPath("$[1].id").value(itemDtoLong2.getId()))
                .andExpect(jsonPath("$[1].name").value(itemDtoLong2.getName()))
                .andExpect(jsonPath("$[1].description").value(itemDtoLong2.getDescription()))
                .andExpect(jsonPath("$[1].available").value(itemDtoLong2.getAvailable()));
    }

    @Test
    void findItemsByTextTest() throws Exception {
        List<ItemDto> items = List.of(itemDto1, itemDto2);
        when(service.find(anyString(), anyInt(), anyInt())).thenReturn(items);
        mockMvc.perform(get("/items/search?text=test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(Constant.userId, 1L)
                        .content(mapper.writeValueAsString(itemDtoLong1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(itemDto1.getId()))
                .andExpect(jsonPath("$[0].name").value(itemDto1.getName()))
                .andExpect(jsonPath("$[0].description").value(itemDto1.getDescription()))
                .andExpect(jsonPath("$[0].available").value(itemDto1.getAvailable()))
                .andExpect(jsonPath("$[0].requestId").value((itemDto1.getRequestId())))
                .andExpect(jsonPath("$[1].id").value(itemDto2.getId()))
                .andExpect(jsonPath("$[1].name").value(itemDto2.getName()))
                .andExpect(jsonPath("$[1].description").value(itemDto2.getDescription()))
                .andExpect(jsonPath("$[1].available").value(itemDto2.getAvailable()))
                .andExpect(jsonPath("$[1].requestId").value((itemDto2.getRequestId())));
    }

    @Test
    void addCommentTest() throws Exception {
        CommentDto commentDto = new CommentDto(1L, "commentText", "authorName", LocalDateTime.now());
        when(service.addComment(any(), anyLong(), anyLong())).thenReturn(commentDto);
        mockMvc.perform(post("/items/1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(Constant.userId, 1L)
                        .content(mapper.writeValueAsString(commentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commentDto.getId()))
                .andExpect(jsonPath("$.text").value(commentDto.getText()))
                .andExpect(jsonPath("$.authorName").value(commentDto.getAuthorName()));
    }
}
