package com.Link.cartService.Exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotFound extends RuntimeException {
    public NotFound(String message) {
        super(message);
    }
}
