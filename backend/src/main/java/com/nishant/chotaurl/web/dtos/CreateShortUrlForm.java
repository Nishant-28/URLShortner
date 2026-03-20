package com.nishant.chotaurl.web.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateShortUrlForm(
        @NotBlank(message = "original URL is required")
        String originalUrl,
        Boolean isPrivate,
        @Min(1)
        @Max(375)
        Integer expirationInDays
) {
}
