package com.nishant.chotaurl.domain.exceptions;

public class ShortUrlNotFoundException extends RuntimeException {
    public ShortUrlNotFoundException (String message) {
        super(message);
    }
}
