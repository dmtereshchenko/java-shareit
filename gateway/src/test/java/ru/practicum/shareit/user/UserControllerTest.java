package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;
    private final UserDto userDto1 = new UserDto(1L, "userName1", "usermail1@test.test");
    private final UserDto userDto2 = new UserDto(2L, "userName2", "usermail2@test.test");
    @MockBean
    private UserClient client;

    @Test
    void createTest() throws Exception {
        when(client.create(any())).thenReturn(ResponseEntity.of(Optional.of(userDto1)));
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto1.getId()))
                .andExpect(jsonPath("$.name").value(userDto1.getName()))
                .andExpect(jsonPath("$.email").value(userDto1.getEmail()));
    }

    @Test
    void createEmptyUserNameTest() throws Exception {
        UserDto userDto3 = new UserDto(3L, "", "userEmail3@test.test");
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDto3)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createEmptyEmailTest() throws Exception {
        UserDto userDto3 = new UserDto(3L, "userName3", "");
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDto3)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createInvalidEmailTest() throws Exception {
        UserDto userDto3 = new UserDto(3L, "userName3", "usermail3test.test");
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDto3)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUserTest() throws Exception {
        UserDto userDto3 = new UserDto(1L, "userUpdateName1", "userUpdateMail1@test.test");
        when(client.update(any(), anyLong())).thenReturn(ResponseEntity.of(Optional.of(userDto3)));
        mockMvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDto3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto3.getId()))
                .andExpect(jsonPath("$.name").value(userDto3.getName()))
                .andExpect(jsonPath("$.email").value(userDto3.getEmail()));
    }

    @Test
    void getUserTest() throws Exception {
        when(client.get(anyLong())).thenReturn(ResponseEntity.of(Optional.of(userDto1)));
        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto1.getId()))
                .andExpect(jsonPath("$.name").value(userDto1.getName()))
                .andExpect(jsonPath("$.email").value(userDto1.getEmail()));
    }

    @Test
    void getAllUsersTest() throws Exception {
        List<UserDto> users = List.of(userDto1, userDto2);
        when(client.getAll()).thenReturn(ResponseEntity.of(Optional.of(users)));
        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(users)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(userDto1.getId()))
                .andExpect(jsonPath("$[0].name").value(userDto1.getName()))
                .andExpect(jsonPath("$[0].email").value(userDto1.getEmail()))
                .andExpect(jsonPath("$[1].id").value(userDto2.getId()))
                .andExpect(jsonPath("$[1].name").value(userDto2.getName()))
                .andExpect(jsonPath("$[1].email").value(userDto2.getEmail()));
    }

    @Test
    void deleteUserTest() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }
}
