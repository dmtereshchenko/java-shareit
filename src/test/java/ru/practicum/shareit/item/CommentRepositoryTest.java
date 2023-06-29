package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.storage.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentRepositoryTest {

    Comment comment = new Comment(1L, "commentText", null, null, LocalDateTime.now());
    @Mock
    private CommentRepository commentRepository;

    @Test
    void findCommentsByItemIdTest() {
        when(commentRepository.findCommentsByItemId(anyList())).thenReturn(List.of(comment));
        assertEquals(List.of(comment), commentRepository.findCommentsByItemId(List.of(1L)));
    }
}
