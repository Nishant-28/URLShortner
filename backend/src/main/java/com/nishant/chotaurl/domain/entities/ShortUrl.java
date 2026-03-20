package com.nishant.chotaurl.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "short_urls")
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 10)
    @NotNull
    @Column(name = "short_key", nullable = false, length = 10)
    private String shortKey;

    @NotNull
    @Column(name = "original_url", nullable = false, length = Integer.MAX_VALUE)
    private String originalUrl;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_private", nullable = false)
    private Boolean isPrivate;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "click_count", nullable = false)
    private Long clickCount;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;


}