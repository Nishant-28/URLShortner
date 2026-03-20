package com.nishant.chotaurl.web.controller;

import com.nishant.chotaurl.ApplicationProperties;
import com.nishant.chotaurl.domain.models.CreateShortUrlCmd;
import com.nishant.chotaurl.domain.models.PagedResult;
import com.nishant.chotaurl.domain.models.ShortUrlDto;
import com.nishant.chotaurl.domain.services.ShortUrlService;
import com.nishant.chotaurl.web.dtos.CreateShortUrlForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final ShortUrlService shortUrlService;
    private final ApplicationProperties applicationProperties;
    private final SecurityUtils securityUtils;

    @GetMapping("/")
    public PagedResult<ShortUrlDto> home(
            @RequestParam(defaultValue = "1") Integer page) {
        return shortUrlService.findAllPublicShortUrls(page, applicationProperties.pageSize());
    }

    @PostMapping("/short-urls")
    public ShortUrlDto createShortUrl(
            @RequestBody @Valid CreateShortUrlForm form) {
        Long userId = securityUtils.getCurrentUserId();
        CreateShortUrlCmd cmd = new CreateShortUrlCmd(
                form.originalUrl(),
                form.isPrivate(),
                form.expirationInDays(),
                userId);
        return shortUrlService.createShortUrl(cmd);
    }

    @GetMapping("/s/{shortKey}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortKey) {
        Long userId = securityUtils.getCurrentUserId();
        Optional<ShortUrlDto> shortUrlDtoOptional = shortUrlService.accessShortUrl(shortKey, userId);
        if (shortUrlDtoOptional.isEmpty()) {
            throw new RuntimeException("Invalid Short key: " + shortKey);
        }
        ShortUrlDto shortUrlDto = shortUrlDtoOptional.get();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(shortUrlDto.originalUrl()))
                .build();
    }

    @GetMapping("/my-urls")
    public PagedResult<ShortUrlDto> showUserUrls(
            @RequestParam(defaultValue = "1") int page) {
        var currentUserId = securityUtils.getCurrentUserId();
        return shortUrlService.getUserShortUrls(currentUserId, page, applicationProperties.pageSize());
    }

    @PostMapping("/delete-urls")
    public ResponseEntity<String> deleteUrls(
            @RequestBody List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().body("No URLs selected for deletion");
        }
        try {
            var currentUserId = securityUtils.getCurrentUserId();
            shortUrlService.deleteUserShortUrls(ids, currentUserId);
            return ResponseEntity.ok("Selected URLs have been deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting URLs: " + e.getMessage());
        }
    }
}
