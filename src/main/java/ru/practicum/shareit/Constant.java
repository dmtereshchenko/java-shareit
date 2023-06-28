package ru.practicum.shareit;

import lombok.Getter;
import org.springframework.data.domain.Sort;

import java.time.format.DateTimeFormatter;

@Getter
public class Constant {

    public static final String userId = "X-Sharer-User-Id";
    public static final Sort sortDesc = Sort.by(Sort.Direction.DESC, "start");
    public static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
}
