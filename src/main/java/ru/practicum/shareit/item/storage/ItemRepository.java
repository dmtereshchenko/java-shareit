package ru.practicum.shareit.item.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByOwnerId(long userId, Pageable pageable);

    List<Item> findAllByDescriptionContainingIgnoreCaseAndAvailableIsTrue(String text, Pageable pageable);

    List<Item> findAllByRequestIdInOrderByIdDesc(List<Long> ids);
}
