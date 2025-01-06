package com.Link.cartService.Exception;

import lombok.Data;

@Data
public  class ClientErrorException extends RuntimeException {

    private final int statusCode;

    public ClientErrorException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

