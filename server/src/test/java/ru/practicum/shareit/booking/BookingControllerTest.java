package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.controller.BookingController;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.Status;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.Constant.formatter;
import static ru.practicum.shareit.Constant.getId;

@WebMvcTest(controllers = BookingController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingControllerTest {

    private final ObjectMapper mapper;
    private final MockMvc mockMvc;
    private final UserDto user = new UserDto(1L, "userName", "userMail@test.test");
    private final ItemDto item = new ItemDto(1L, "itemName", "itemDescription", true, 1L);
    private final BookingDto bookingDto1 = new BookingDto(1L, item.getId(), LocalDateTime.now().plusHours(1),
            LocalDateTime.now().plusHours(2), item, user, Status.WAITING);
    private final BookingDto bookingDto2 = new BookingDto(2L, item.getId(), LocalDateTime.now().plusHours(1),
            LocalDateTime.now().plusHours(2), item, user, Status.WAITING);
    @MockBean
    private BookingService service;

    @Test
    void createBookingTest() throws Exception {
        when(service.create(any(), anyLong())).thenReturn(bookingDto1);
        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(getId, 1L)
                        .content(mapper.writeValueAsString(bookingDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookingDto1.getId()))
                .andExpect(jsonPath("$.item.id").value(bookingDto1.getItemId()))
                .andExpect(jsonPath("$.item.name").value(bookingDto1.getItem().getName()))
                .andExpect(jsonPath("$.start").value(bookingDto1.getStart().format(formatter)))
                .andExpect(jsonPath("$.end").value(bookingDto1.getEnd().format(formatter)))
                .andExpect(jsonPath("$.booker.id").value(bookingDto1.getBooker().getId()))
                .andExpect(jsonPath("$.booker.name").value(bookingDto1.getBooker().getName()))
                .andExpect(jsonPath("$.status").value(Status.WAITING.name()));
    }

    @Test
    void updateBookingTest() throws Exception {
        BookingDto bookingDto3 = new BookingDto(1L, item.getId(), LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2), item, user, Status.APPROVED);
        when(service.update(anyLong(), anyBoolean(), anyLong())).thenReturn(bookingDto3);
        mockMvc.perform(patch("/bookings/1?approved=true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(getId, 1L)
                        .content(mapper.writeValueAsString(bookingDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookingDto3.getId()))
                .andExpect(jsonPath("$.item.id").value(bookingDto3.getItemId()))
                .andExpect(jsonPath("$.item.name").value(bookingDto3.getItem().getName()))
                .andExpect(jsonPath("$.start").value(bookingDto3.getStart().format(formatter)))
                .andExpect(jsonPath("$.end").value(bookingDto3.getEnd().format(formatter)))
                .andExpect(jsonPath("$.booker.id").value(bookingDto3.getBooker().getId()))
                .andExpect(jsonPath("$.booker.name").value(bookingDto3.getBooker().getName()))
                .andExpect(jsonPath("$.status").value(Status.APPROVED.name()));
    }

    @Test
    void getBookingTest() throws Exception {
        when(service.get(anyLong(), anyLong())).thenReturn(bookingDto1);
        mockMvc.perform(get("/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(getId, 1L)
                        .content(mapper.writeValueAsString(bookingDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookingDto1.getId()))
                .andExpect(jsonPath("$.item.id").value(bookingDto1.getItemId()))
                .andExpect(jsonPath("$.item.name").value(bookingDto1.getItem().getName()))
                .andExpect(jsonPath("$.start").value(bookingDto1.getStart().format(formatter)))
                .andExpect(jsonPath("$.end").value(bookingDto1.getEnd().format(formatter)))
                .andExpect(jsonPath("$.booker.id").value(bookingDto1.getBooker().getId()))
                .andExpect(jsonPath("$.booker.name").value(bookingDto1.getBooker().getName()))
                .andExpect(jsonPath("$.status").value(Status.WAITING.name()));
    }

    @Test
    void getAllByBookerTest() throws Exception {
        List<BookingDto> bookings = List.of(bookingDto1, bookingDto2);
        when(service.getAllByBooker(anyString(), anyLong(), anyInt(), anyInt())).thenReturn(bookings);
        mockMvc.perform(get("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(getId, 1L)
                        .content(mapper.writeValueAsString(bookings))
                        .param("state", "ALL")
                        .param("from", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(bookingDto1.getId()))
                .andExpect(jsonPath("$[0].item.id").value(bookingDto1.getItemId()))
                .andExpect(jsonPath("$[0].item.name").value(bookingDto1.getItem().getName()))
                .andExpect(jsonPath("$[0].start").value(bookingDto1.getStart().format(formatter)))
                .andExpect(jsonPath("$[0].end").value(bookingDto1.getEnd().format(formatter)))
                .andExpect(jsonPath("$[0].booker.id").value(bookingDto1.getBooker().getId()))
                .andExpect(jsonPath("$[0].booker.name").value(bookingDto1.getBooker().getName()))
                .andExpect(jsonPath("$[0].status").value(Status.WAITING.name()))
                .andExpect(jsonPath("$[1].id").value(bookingDto2.getId()))
                .andExpect(jsonPath("$[1].item.id").value(bookingDto2.getItemId()))
                .andExpect(jsonPath("$[1].item.name").value(bookingDto2.getItem().getName()))
                .andExpect(jsonPath("$[1].start").value(bookingDto2.getStart().format(formatter)))
                .andExpect(jsonPath("$[1].end").value(bookingDto2.getEnd().format(formatter)))
                .andExpect(jsonPath("$[1].booker.id").value(bookingDto2.getBooker().getId()))
                .andExpect(jsonPath("$[1].booker.name").value(bookingDto2.getBooker().getName()))
                .andExpect(jsonPath("$[1].status").value(Status.WAITING.name()));
    }

    @Test
    void getAllByOwnerTest() throws Exception {
        List<BookingDto> bookings = List.of(bookingDto1, bookingDto2);
        when(service.getAllByOwner(anyString(), anyLong(), anyInt(), anyInt())).thenReturn(bookings);
        mockMvc.perform(get("/bookings/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(getId, 1L)
                        .content(mapper.writeValueAsString(bookings))
                        .param("state", "ALL")
                        .param("from", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(bookingDto1.getId()))
                .andExpect(jsonPath("$[0].item.id").value(bookingDto1.getItemId()))
                .andExpect(jsonPath("$[0].item.name").value(bookingDto1.getItem().getName()))
                .andExpect(jsonPath("$[0].start").value(bookingDto1.getStart().format(formatter)))
                .andExpect(jsonPath("$[0].end").value(bookingDto1.getEnd().format(formatter)))
                .andExpect(jsonPath("$[0].booker.id").value(bookingDto1.getBooker().getId()))
                .andExpect(jsonPath("$[0].booker.name").value(bookingDto1.getBooker().getName()))
                .andExpect(jsonPath("$[0].status").value(Status.WAITING.name()))
                .andExpect(jsonPath("$[1].id").value(bookingDto2.getId()))
                .andExpect(jsonPath("$[1].item.id").value(bookingDto2.getItemId()))
                .andExpect(jsonPath("$[1].item.name").value(bookingDto2.getItem().getName()))
                .andExpect(jsonPath("$[1].start").value(bookingDto2.getStart().format(formatter)))
                .andExpect(jsonPath("$[1].end").value(bookingDto2.getEnd().format(formatter)))
                .andExpect(jsonPath("$[1].booker.id").value(bookingDto2.getBooker().getId()))
                .andExpect(jsonPath("$[1].booker.name").value(bookingDto2.getBooker().getName()))
                .andExpect(jsonPath("$[1].status").value(Status.WAITING.name()));
    }
}
