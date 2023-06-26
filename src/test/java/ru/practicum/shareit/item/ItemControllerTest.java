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
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .andExpect(jsonPath("$.available").value(true))
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
}
