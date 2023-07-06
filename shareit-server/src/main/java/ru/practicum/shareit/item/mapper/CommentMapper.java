package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Objects;

public class CommentMapper {

    public static Comment toComment(CommentDto commentDto, User user, Item item) {
        return new Comment(
                commentDto.getId(),
                commentDto.getText(),
                item,
                user,
                Objects.requireNonNullElse(commentDto.getCreated(), LocalDateTime.now())
        );
    }

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getAuthor().getName(),
                comment.getCreated()
        );
    }
}
