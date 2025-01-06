package com.Link.cartService.Exception;

import lombok.Data;

@Data
public class ServerErrorException extends RuntimeException {

    private final int statusCode;

    public ServerErrorException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
