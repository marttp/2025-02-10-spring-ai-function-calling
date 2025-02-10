package dev.tpcoder.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record News(
        String author,
        String title,
        String description,
        String url,
        String source,
        String image,
        String category,
        String language,
        String country,
        @JsonProperty("published_at")
        OffsetDateTime publishedAt
) {
}
