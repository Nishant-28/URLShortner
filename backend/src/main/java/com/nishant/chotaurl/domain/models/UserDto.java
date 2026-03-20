package com.nishant.chotaurl.domain.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.nishant.chotaurl.domain.entities.User}
 */
public record UserDto(Long id, @NotNull @Size(max = 100) String name) implements Serializable {
}