package dev.tpcoder.application.dto;

import java.time.LocalDate;
import java.util.List;

public record GetNewsRequest(
        List<String> countries,
        String category,
        LocalDate rangeStart,
        LocalDate rangeEnd
) {
}
