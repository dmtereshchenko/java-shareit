package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentMapperTest {

    LocalDateTime now = LocalDateTime.now();
    User user = new User(1L, "authorName", "authorMail@test.test");
    Comment comment = new Comment(1L, "commentText", null, user, now);
    CommentDto commentDto = new CommentDto(1L, "commentText", "authorName", now);

    @Test
    void fromCommentDtoToCommentTest() {
        assertEquals(comment, CommentMapper.toComment(commentDto, user, null));
    }

    @Test
    void fromCommentToCommentDtoTest() {
        assertEquals(commentDto, CommentMapper.toDto(comment));
    }

}
