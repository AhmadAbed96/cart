package com.Link.cartService.Exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotFond extends RuntimeException {
    public NotFond(String message) {
        super(message);
    }
}
