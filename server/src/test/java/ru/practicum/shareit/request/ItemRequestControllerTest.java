package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.controller.ItemRequestController;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.Constant.formatter;
import static ru.practicum.shareit.Constant.getId;

@WebMvcTest(controllers = ItemRequestController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRequestControllerTest {

    private final ObjectMapper mapper;
    private final MockMvc mockMvc;
    private final ItemRequestDto itemRequestDto1 = new ItemRequestDto(1L, "itemRequestDescription1", LocalDateTime.now(), new ArrayList<>());
    private final ItemRequestDto itemRequestDto2 = new ItemRequestDto(2L, "itemRequestDescription2", LocalDateTime.now(), new ArrayList<>());
    @MockBean
    private ItemRequestService service;

    @Test
    void createItemRequestTest() throws Exception {
        when(service.create(any(), anyLong())).thenReturn(itemRequestDto1);
        mockMvc.perform(post("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(getId, 1L)
                        .content(mapper.writeValueAsString(itemRequestDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemRequestDto1.getId()))
                .andExpect(jsonPath("$.description").value(itemRequestDto1.getDescription()))
                .andExpect(jsonPath("$.created").value(itemRequestDto1.getCreated().format(formatter)))
                .andExpect(jsonPath("$.items").isEmpty());
    }

    @Test
    void getRequestTest() throws Exception {
        when(service.get(anyLong(), anyLong())).thenReturn(itemRequestDto1);
        mockMvc.perform(get("/requests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(getId, 1L)
                        .content(mapper.writeValueAsString(itemRequestDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemRequestDto1.getId()))
                .andExpect(jsonPath("$.description").value(itemRequestDto1.getDescription()))
                .andExpect(jsonPath("$.created").value(itemRequestDto1.getCreated().format(formatter)))
                .andExpect(jsonPath("$.items").isEmpty());
    }

    @Test
    void getAllTest() throws Exception {
        when(service.getAll(anyLong(), anyInt(), anyInt())).thenReturn(List.of(itemRequestDto1, itemRequestDto2));
        mockMvc.perform(get("/requests/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(getId, 1L)
                        .content(mapper.writeValueAsString(itemRequestDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(itemRequestDto1.getId()))
                .andExpect(jsonPath("$[0].description").value(itemRequestDto1.getDescription()))
                .andExpect(jsonPath("$[0].created").value(itemRequestDto1.getCreated().format(formatter)))
                .andExpect(jsonPath("$[0].items").isEmpty())
                .andExpect(jsonPath("$[1].id").value(itemRequestDto2.getId()))
                .andExpect(jsonPath("$[1].description").value(itemRequestDto2.getDescription()))
                .andExpect(jsonPath("$[1].created").value(itemRequestDto2.getCreated().format(formatter)))
                .andExpect(jsonPath("$[1].items").isEmpty());
    }

    @Test
    void getAllByOwnerTest() throws Exception {
        when(service.getAllByOwner(anyLong())).thenReturn(List.of(itemRequestDto1, itemRequestDto2));
        mockMvc.perform(get("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(getId, 1L)
                        .content(mapper.writeValueAsString(itemRequestDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(itemRequestDto1.getId()))
                .andExpect(jsonPath("$[0].description").value(itemRequestDto1.getDescription()))
                .andExpect(jsonPath("$[0].created").value(itemRequestDto1.getCreated().format(formatter)))
                .andExpect(jsonPath("$[0].items").isEmpty())
                .andExpect(jsonPath("$[1].id").value(itemRequestDto2.getId()))
                .andExpect(jsonPath("$[1].description").value(itemRequestDto2.getDescription()))
                .andExpect(jsonPath("$[1].created").value(itemRequestDto2.getCreated().format(formatter)))
                .andExpect(jsonPath("$[1].items").isEmpty());
    }
}
