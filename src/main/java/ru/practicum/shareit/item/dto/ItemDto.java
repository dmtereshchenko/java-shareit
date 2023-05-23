package ru.practicum.shareit.item.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ItemDto {

    int id;
    @NotBlank
    String name;
    @NotBlank
    String description;
    Boolean available;
    int requestId;
}
