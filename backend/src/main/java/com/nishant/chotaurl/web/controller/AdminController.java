package com.nishant.chotaurl.web.controller;

import com.nishant.chotaurl.ApplicationProperties;
import com.nishant.chotaurl.domain.models.PagedResult;
import com.nishant.chotaurl.domain.models.ShortUrlDto;
import com.nishant.chotaurl.domain.services.ShortUrlService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final ShortUrlService shortUrlService;
    private final ApplicationProperties properties;

    public AdminController(ShortUrlService shortUrlService, ApplicationProperties properties) {
        this.shortUrlService = shortUrlService;
        this.properties = properties;
    }

    @GetMapping("/dashboard")
    public PagedResult<ShortUrlDto> dashboard(
            @RequestParam(defaultValue = "1") int page) {
        return shortUrlService.findAllShortUrls(page, properties.pageSize());
    }
}