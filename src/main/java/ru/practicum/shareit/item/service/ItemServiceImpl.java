package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exceptions.AccessDeniedException;
import ru.practicum.shareit.exceptions.ItemNotFoundException;
import ru.practicum.shareit.exceptions.UserNotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoLong;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.CommentRepository;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public ItemDto create(ItemDto itemDto, long userId) {
        return ItemMapper.toDto(itemRepository.save(ItemMapper.toItem(itemDto, userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId)))));
    }

    @Override
    public ItemDto update(ItemDto itemDto, long userId, long itemId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (userId != user.getId()) throw new AccessDeniedException("Редактировать вещь может только ее владелец.");
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
        item.setName(itemDto.getName() != null ? itemDto.getName() : item.getName());
        item.setDescription(itemDto.getDescription() != null ? itemDto.getDescription() : item.getDescription());
        item.setAvailable(itemDto.getAvailable() != null ? itemDto.getAvailable() : item.getAvailable());
        return ItemMapper.toDto(itemRepository.save(item));

    }

    @Override
    public ItemDtoLong get(long itemId, long userId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId);
        ItemDtoLong itemDtoLong = ItemMapper.toDtoLong(item);
        if (userId == item.getOwner().getId()) {
            LocalDateTime now = LocalDateTime.now();
            List<Booking> bookings = bookingRepository.getPreviousAndNextBookings(List.of(itemId), now);
            for (Booking booking : bookings) {
                if (booking.getStart().isBefore(now)) {
                    itemDtoLong.setLastBooking(BookingMapper.toBookingDtoShort(booking));
                } else {
                    itemDtoLong.setNextBooking(BookingMapper.toBookingDtoShort(booking));
                }
            }
        }
        return setComments(Map.of(itemDtoLong.getId(), itemDtoLong), commentRepository.findCommentsByItemId(new ArrayList<>(List.of(item.getId())))).get(0);
    }

    @Override
    public List<ItemDtoLong> getAll(long userId) {
        Map<Long, ItemDtoLong> items = itemRepository.findByOwnerId(userId).stream().collect(Collectors.toMap(Item::getId, ItemMapper::toDtoLong));
        LocalDateTime now = LocalDateTime.now();
        List<Booking> bookings = bookingRepository.getPreviousAndNextBookings(new ArrayList<>(items.keySet()), now);
        for (Booking booking : bookings) {
            if (booking.getStart().isBefore(now)) {
                items.get(booking.getItem().getId()).setLastBooking(BookingMapper.toBookingDtoShort(booking));
            } else {
                items.get(booking.getItem().getId()).setNextBooking(BookingMapper.toBookingDtoShort(booking));
            }
        }
        return setComments(items, commentRepository.findCommentsByItemId(new ArrayList<>(items.keySet())));
    }

    @Override
    public void delete(long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    public List<ItemDto> find(String text) {
        return (text.isBlank() ? new ArrayList<>() : itemRepository.findAllByDescriptionContainingIgnoreCaseAndAvailableIsTrue(text).stream().map(ItemMapper::toDto).collect(Collectors.toList()));
    }

    @Override
    public CommentDto addComment(CommentDto commentDto, long userId, long itemId) {
        if (!bookingRepository.findBookingsByBookerIdAndItemIdAndEndIsBeforeAndStatus(userId, itemId, LocalDateTime.now(), Status.APPROVED).isEmpty()) {
            return CommentMapper.toDto(commentRepository.save(CommentMapper.toComment(commentDto,
                    userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId)),
                    itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId)))));
        } else {
            throw new AccessDeniedException("Вы не можете оставить комментарий к этой вещи");
        }
    }

    private List<ItemDtoLong> setComments(Map<Long, ItemDtoLong> items, List<Comment> comments) {
        for (Comment comment : comments) {
            items.get(comment.getItem().getId()).addComment(CommentMapper.toDto(comment));
        }
        return new ArrayList<>(items.values());
    }
}
