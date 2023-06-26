package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exceptions.ItemRequestNotFoundException;
import ru.practicum.shareit.exceptions.UserNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.storage.ItemRequestRepository;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDto create(ItemRequestDto itemRequestDto, long userId) {
        return ItemRequestMapper.toDto(itemRequestRepository.save(ItemRequestMapper.toItemRequest(itemRequestDto,
                userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId)))));
    }

    @Override
    public ItemRequestDto get(long userId, long requestId) {
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId);
        return setItemsToRequests(Map.of(requestId, ItemRequestMapper.toDto(itemRequestRepository.findById(requestId).
                orElseThrow(() -> new ItemRequestNotFoundException(requestId))))).get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemRequestDto> getAll(long userId, int from, int size) {
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId);
        return setItemsToRequests(itemRequestRepository.findItemRequestsByRequesterIdNotOrderByCreatedDesc(userId, PageRequest.of(from / size, size))
                .stream().map(ItemRequestMapper::toDto).collect(Collectors.toList()).stream().collect(Collectors.toMap(k -> k.getId(), v -> v)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemRequestDto> getAllByOwner(long userId) {
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId);
        return setItemsToRequests(itemRequestRepository.findItemRequestByRequesterId(userId, Sort.by(Sort.Direction.DESC, "created"))
                .stream().map(ItemRequestMapper::toDto).collect(Collectors.toList()).stream().collect(Collectors.toMap(k -> k.getId(), v -> v)));
    }

    private List<ItemRequestDto> setItemsToRequests(Map<Long, ItemRequestDto> itemRequests) {
        List<ItemDto> items = itemRepository.findAllByRequestIdInOrderByIdDesc(itemRequests.values().stream().map(ItemRequestDto::getId).collect(Collectors.toList()))
                .stream().map(ItemMapper::toDto).collect(Collectors.toList());
        for (ItemDto itemDto : items) {
            itemRequests.get(itemDto.getRequestId()).addItemDto(itemDto);
        }
        return itemRequests.values().stream().collect(Collectors.toList());
    }
}
