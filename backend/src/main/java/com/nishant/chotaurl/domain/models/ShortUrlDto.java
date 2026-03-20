package com.nishant.chotaurl.domain.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.nishant.chotaurl.domain.entities.ShortUrl}
 */
public record ShortUrlDto(Long id,
                          @NotNull @Size(max = 10) String shortKey,
                          @NotNull String originalUrl,
                          @NotNull Boolean isPrivate,
                          Instant expiresAt,
                          @NotNull Long clickCount,
                          @NotNull Instant createdAt,
                          UserDto createdBy) implements Serializable {
}