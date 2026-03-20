package com.nishant.chotaurl.domain.services;

import com.nishant.chotaurl.domain.entities.ShortUrl;
import com.nishant.chotaurl.domain.entities.User;
import com.nishant.chotaurl.domain.models.ShortUrlDto;
import com.nishant.chotaurl.domain.models.UserDto;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    public ShortUrlDto toShortUrlDto(ShortUrl shortUrl) {
        UserDto userDto = null;
        if (shortUrl.getCreatedBy() != null) {
            userDto = toUserDto(shortUrl.getCreatedBy());
        }

        return new ShortUrlDto(
                shortUrl.getId(),
                shortUrl.getShortKey(),
                shortUrl.getOriginalUrl(),
                shortUrl.getIsPrivate(),
                shortUrl.getExpiresAt(),
                shortUrl.getClickCount(),
                shortUrl.getCreatedAt(),
                userDto);
    }

    private UserDto toUserDto(User createdBy) {
        return new UserDto(createdBy.getId(), createdBy.getName());
    }
}
