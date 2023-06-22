package ru.practicum.shareit;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public class Constant {

    public static final String userId = "X-Sharer-User-Id";
    public static final Sort sortDesc = Sort.by(Sort.Direction.DESC, "start");
}
