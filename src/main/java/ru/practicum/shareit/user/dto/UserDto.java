package ru.practicum.shareit.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {

    private final Long id;
    @NotBlank
    private final String name;
    @NotBlank
    @Email
    private final String email;
}
